package com.emakarovas.animalrescue.persistence.dao.impl

import scala.concurrent.Future

import com.emakarovas.animalrescue.model.OfferModel
import com.emakarovas.animalrescue.model.constants.AnimalTypeDetailsConstants
import com.emakarovas.animalrescue.model.constants.LocationConstants
import com.emakarovas.animalrescue.model.constants.OfferAnimalConstants
import com.emakarovas.animalrescue.model.constants.OfferConstants
import com.emakarovas.animalrescue.model.enumeration.Animal
import com.emakarovas.animalrescue.model.enumeration.AnimalType
import com.emakarovas.animalrescue.model.enumeration.SpecificType
import com.emakarovas.animalrescue.persistence.dao.OfferDAO
import com.emakarovas.animalrescue.persistence.dao.constants.FacetConstants
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants
import com.emakarovas.animalrescue.persistence.dao.search.OfferFacet
import com.emakarovas.animalrescue.persistence.dao.search.OfferFacetReader
import com.emakarovas.animalrescue.persistence.dao.search.OfferSearch
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionType
import com.emakarovas.animalrescue.persistence.reader.OfferReader
import com.emakarovas.animalrescue.persistence.writer.OfferWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.AnimalTypeWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.ColorWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.GenderWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.SizeWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.SpecificTypeWriter
import com.emakarovas.animalrescue.persistence.writer.property.OfferPropertyWriter
import com.emakarovas.animalrescue.util.generator.StringGenerator

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.api.Cursor
import reactivemongo.api.indexes.IndexType
import reactivemongo.bson.BSONArray
import reactivemongo.bson.BSONDocument
import reactivemongo.bson.BSONString

@Singleton
class DefaultOfferDAO @Inject() (
    val colFactory: MongoCollectionFactory,
    val stringGenerator: StringGenerator,
    implicit override val writer: OfferWriter,
    implicit override val reader: OfferReader,
    implicit override val propertyWriter: OfferPropertyWriter,
    implicit val animalTypeWriter: AnimalTypeWriter,
    implicit val genderWriter: GenderWriter,
    implicit val specificTypeWriter: SpecificTypeWriter,
    implicit val colorWriter: ColorWriter,
    implicit val sizeWriter: SizeWriter,
    val offerFacetReader: OfferFacetReader) extends OfferDAO {
  
  val collection = colFactory.getCollection(MongoCollectionType.Offer)
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  val indexList = List(
      buildIndex(OfferConstants.Url, IndexType.Ascending, true),
      buildIndex(OfferConstants.OfferAnimalList + "." + MongoConstants.MongoId, IndexType.Ascending, true),
      buildIndex(OfferConstants.OfferAnimalList + "." + OfferAnimalConstants.AnimalTypeDetails 
          + "." + AnimalTypeDetailsConstants.AnimalType, IndexType.Ascending, false),
      buildIndex(OfferConstants.OfferAnimalList + "." + OfferAnimalConstants.AnimalTypeDetails 
          + "." + AnimalTypeDetailsConstants.SpecificTypeSet, IndexType.Ascending, false),
      buildIndex(OfferConstants.OfferAnimalList + "." + OfferAnimalConstants.Gender, IndexType.Ascending, false),
      buildIndex(OfferConstants.OfferAnimalList + "." + OfferAnimalConstants.Age, IndexType.Ascending, false),
      buildIndex(OfferConstants.Location + "." + LocationConstants.Country, IndexType.Ascending, false),
      buildIndex(OfferConstants.Location + "." + LocationConstants.City, IndexType.Ascending, false),
      buildIndex(OfferConstants.Location + "." + LocationConstants.Geolocation, IndexType.Geo2DSpherical, false)
  )
  setUpIndexes(indexList)
  
  override def findByUserId(userId: String): Future[List[OfferModel]] = {
    val selector = BSONDocument(OfferConstants.UserId -> userId)
    collection.flatMap(_.find(selector)
        .cursor[OfferModel]()
        .collect[List](Int.MaxValue, Cursor.FailOnError[List[OfferModel]]()))
  }
  
  override def addToViewedByUserIdSetById(offerId: String, userId: String): Future[Int] = {
    val selector = BSONDocument(MongoConstants.MongoId -> offerId)
    val update = BSONDocument("$addToSet" -> BSONDocument(
        OfferConstants.ViewedByUserIdSet -> userId))
    collection.flatMap(_.update(selector, update)).map(writeRes => writeRes.nModified)
  }
  
  override def findByOfferSearch(offerSearch: OfferSearch[_ <: Animal], count: Int): Future[List[OfferModel]] = {
    val matchQuery = getOfferSearchMatchQuery(offerSearch)
    val filterQuery = getOfferAnimalSearchFilterQuery(offerSearch)
    collection.flatMap(col => {
      import col.BatchCommands.AggregationFramework.{
        Match, Project, Filter
      }
      col.aggregate1(
          Match(matchQuery),
          List(
            Project(BSONDocument(
                OfferConstants.Url -> 1,
                OfferConstants.StartDate -> 1,
                OfferConstants.EndDate -> 1,
                OfferConstants.Text -> 1,
                OfferConstants.OfferAnimalList -> Filter(
                    BSONString("$" + OfferConstants.OfferAnimalList),
                    MongoConstants.OfferAnimalFilterAlias,
                    filterQuery),
                OfferConstants.Image -> 1,
                OfferConstants.Video -> 1,
                OfferConstants.Location -> 1,
                OfferConstants.CommentList -> 1,
                OfferConstants.ViewedByUserIdSet -> 1,
                OfferConstants.UserId -> 1
              )
            )
          ), batchSize=Some(1)
      ).collect[List](count, Cursor.FailOnError[List[OfferModel]]())
              
    })

  }
  
  override def getFacetSearchData(offerSearch: OfferSearch[_ <: Animal]): Future[OfferFacet] = {
    val matchQuery = getOfferSearchMatchQuery(offerSearch)
    val filterQuery = getOfferAnimalSearchFilterQuery(offerSearch)
    collection.flatMap(col => {
      import col.BatchCommands.AggregationFramework.{
        Match, Project, Filter, PipelineOperator, UnwindField
      }

      col.aggregate(
          Match(matchQuery),
          List(
            Project(BSONDocument(
                OfferConstants.Url -> 1,
                OfferConstants.StartDate -> 1,
                OfferConstants.EndDate -> 1,
                OfferConstants.Text -> 1,
                OfferConstants.OfferAnimalList -> Filter(
                    BSONString("$" + OfferConstants.OfferAnimalList),
                    MongoConstants.OfferAnimalFilterAlias,
                    filterQuery),
                OfferConstants.Image -> 1,
                OfferConstants.Video -> 1,
                OfferConstants.Location -> 1,
                OfferConstants.CommentList -> 1,
                OfferConstants.ViewedByUserIdSet -> 1,
                OfferConstants.UserId -> 1
              )
            ),
            UnwindField(OfferConstants.OfferAnimalList),
            PipelineOperator(
                BSONDocument("$facet" -> 
                    BSONDocument(
                        FacetConstants.AnimalType -> 
                          BSONArray(
                            BSONDocument("$sortByCount" -> 
                              ("$" + OfferConstants.OfferAnimalList + "." 
                                + OfferAnimalConstants.AnimalTypeDetails + "." 
                                  + AnimalTypeDetailsConstants.AnimalType)
                          )),
                        FacetConstants.SpecificType ->
                          BSONArray(
                            BSONDocument("$unwind" ->
                              ("$" + OfferConstants.OfferAnimalList + "." 
                                + OfferAnimalConstants.AnimalTypeDetails + "." 
                                  + AnimalTypeDetailsConstants.SpecificTypeSet)
                            ),
                            BSONDocument("$sortByCount" ->
                              ("$" + OfferConstants.OfferAnimalList + "." 
                                + OfferAnimalConstants.AnimalTypeDetails + "." 
                                  + AnimalTypeDetailsConstants.SpecificTypeSet)
                          )),
                        FacetConstants.Gender ->
                          BSONArray(
                            BSONDocument("$match" ->
                              BSONDocument(OfferConstants.OfferAnimalList + "." + OfferAnimalConstants.Gender ->
                                BSONDocument("$exists" -> 1)
                              )
                            ),
                            BSONDocument("$sortByCount" ->
                              ("$" + OfferConstants.OfferAnimalList + "."
                                + OfferAnimalConstants.Gender)
                          )),
                        FacetConstants.Color ->
                          BSONArray(
                            BSONDocument("$unwind" ->
                              ("$" + OfferConstants.OfferAnimalList + "."
                                + OfferAnimalConstants.ColorSet)
                            ),
                            BSONDocument("$sortByCount" ->
                              ("$" + OfferConstants.OfferAnimalList + "."
                                + OfferAnimalConstants.ColorSet)
                          )),
                        FacetConstants.Size ->
                          BSONArray(
                            BSONDocument("$sortByCount" ->
                              ("$" + OfferConstants.OfferAnimalList + "."
                                + OfferAnimalConstants.Size)
                          )),
                        FacetConstants.Tag ->
                          BSONArray(
                            BSONDocument("$unwind" ->
                              ("$" + OfferConstants.OfferAnimalList + "."
                                + OfferAnimalConstants.TagSet)
                            ),
                            BSONDocument("$sortByCount" ->
                              ("$" + OfferConstants.OfferAnimalList + "."
                                + OfferAnimalConstants.TagSet)
                          )),
                        FacetConstants.Country ->
                          BSONArray(
                            BSONDocument("$sortByCount" ->
                              ("$" + OfferConstants.Location + "."
                                + LocationConstants.Country)
                          )),
                        FacetConstants.City ->
                          BSONArray(
                            BSONDocument("$sortByCount" ->
                              ("$" + OfferConstants.Location + "."
                                + LocationConstants.City)
                          )),
                        FacetConstants.Castrated ->
                          BSONArray(
                            BSONDocument("$sortByCount" ->
                              ("$" + OfferConstants.OfferAnimalList + "."
                                + OfferAnimalConstants.IsCastrated)
                          ))
                    )
                )
            )
        )
      ).map(res => {
        val list = res.head[OfferFacet](offerFacetReader)
        list(0)
      })
              
    })
  }
  
  private def getOfferSearchMatchQuery(offerSearch: OfferSearch[_ <: Animal]): BSONDocument = {
    var doc = BSONDocument()
    
    if(offerSearch.countrySet.size>0)
      doc = doc ++
      (OfferConstants.Location + "." + LocationConstants.Country -> BSONDocument(
          "$in" -> offerSearch.countrySet))
          
    if(offerSearch.citySet.size>0)
      doc = doc ++
      (OfferConstants.Location + "." + LocationConstants.City -> BSONDocument(
          "$in" -> offerSearch.citySet))
          
    doc
  }
  
  private def getOfferAnimalSearchFilterQuery(offerSearch: OfferSearch[_ <: Animal]): BSONDocument = {
    val animalAlias = "$$" + MongoConstants.OfferAnimalFilterAlias
    var conditionArray = BSONArray()
    
    if(offerSearch.animalType.isDefined)
      conditionArray = conditionArray ++
      BSONDocument("$eq" -> BSONArray(
          animalAlias + "." + OfferAnimalConstants.AnimalTypeDetails + "." + AnimalTypeDetailsConstants.AnimalType,
          animalTypeWriter.write(offerSearch.animalType.get)))
          
    if(offerSearch.specificTypeSet.size>0)
      conditionArray = conditionArray ++
      BSONDocument("$setIsSubset" -> BSONArray(
          specificTypeWriter.writeCollection(offerSearch.specificTypeSet),
          animalAlias + "." + OfferAnimalConstants.AnimalTypeDetails + "." + AnimalTypeDetailsConstants.SpecificTypeSet))
          
    if(offerSearch.gender.isDefined)
      conditionArray = conditionArray ++
      BSONDocument("$eq" -> BSONArray(
          animalAlias + "." + OfferAnimalConstants.Gender,
          offerSearch.gender))
          
    if(offerSearch.colorSet.size>0)
      conditionArray = conditionArray ++
      BSONDocument("$setIsSubset" -> BSONArray(
          offerSearch.colorSet,
          animalAlias + "." + OfferAnimalConstants.ColorSet))
          
    if(offerSearch.sizeSet.size>0)
      conditionArray = conditionArray ++
      BSONDocument("$in" -> BSONArray(
          animalAlias + "." + OfferAnimalConstants.Size,
          offerSearch.sizeSet))
          
    if(offerSearch.tagSet.size>0)
      conditionArray = conditionArray ++
      BSONDocument("$setIsSubset" -> BSONArray(
          offerSearch.tagSet,
          animalAlias + "." + OfferAnimalConstants.TagSet))
          
    conditionArray = conditionArray ++
    BSONDocument("$and" -> BSONArray(
        BSONDocument("$gte" -> BSONArray(animalAlias + "." + OfferAnimalConstants.Age, offerSearch.ageBoundaries.lower)),
        BSONDocument("$lte" -> BSONArray(animalAlias + "." + OfferAnimalConstants.Age, offerSearch.ageBoundaries.higher))
    ))
    
    if(offerSearch.castratedOnly)
      conditionArray = conditionArray ++
      BSONDocument("$eq" -> BSONArray(
          animalAlias + "." + OfferAnimalConstants.IsCastrated,
          true))
 
    val conditionDoc = BSONDocument("$and" -> conditionArray)
    conditionDoc
  }
  
  override def addSpecificTypeById(id: String, offerAnimalId: String, specificType: SpecificType[_ <: Animal]): Future[Int] = {
    val animal = specificType.getAsAnimal
    val animalType = AnimalType.valueOf(animal)
    val selector = BSONDocument(
        MongoConstants.MongoId -> id,
        OfferConstants.OfferAnimalList -> BSONDocument(
            "$elemMatch" -> BSONDocument(
                MongoConstants.MongoId -> offerAnimalId,
                OfferAnimalConstants.AnimalTypeDetails + "." + AnimalTypeDetailsConstants.AnimalType -> animalType)))
    val update = BSONDocument(
        "$addToSet" -> BSONDocument(
            OfferConstants.OfferAnimalList + ".$." + OfferAnimalConstants.AnimalTypeDetails + "." + AnimalTypeDetailsConstants.SpecificTypeSet -> specificTypeWriter.write(specificType)))
    collection.flatMap(_.update(selector, update)).map((wRes) => wRes.nModified)
  }
  
  override def removeSpecificTypeById(id: String, offerAnimalId: String, specificType: SpecificType[_ <: Animal]): Future[Int] = {
    val animal = specificType.getAsAnimal
    val animalType = AnimalType.valueOf(animal)
    val selector = BSONDocument(
        MongoConstants.MongoId -> id,
        OfferConstants.OfferAnimalList -> BSONDocument(
            "$elemMatch" -> BSONDocument(
                MongoConstants.MongoId -> offerAnimalId,
                OfferAnimalConstants.AnimalTypeDetails + "." + AnimalTypeDetailsConstants.AnimalType -> animalType)))
    val update = BSONDocument(
        "$pull" -> BSONDocument(
            OfferConstants.OfferAnimalList + ".$." + OfferAnimalConstants.AnimalTypeDetails + "." + AnimalTypeDetailsConstants.SpecificTypeSet -> specificTypeWriter.write(specificType)))
    collection.flatMap(_.update(selector, update)).map((wRes) => wRes.nModified)
  }
  
}