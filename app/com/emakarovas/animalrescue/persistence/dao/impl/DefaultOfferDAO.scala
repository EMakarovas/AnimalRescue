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
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants
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
import reactivemongo.bson.BSONDocument

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
    implicit val sizeWriter: SizeWriter) extends OfferDAO {
  
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
  
  override def addToViewedByUserIdListById(offerId: String, userId: String): Future[Int] = {
    val selector = BSONDocument(MongoConstants.MongoId -> offerId)
    val update = BSONDocument("$addToSet" -> BSONDocument(
        OfferConstants.ViewedByUserIdSet -> userId))
    collection.flatMap(_.update(selector, update)).map(writeRes => writeRes.nModified)
  }
  
  override def findByOfferSearch(offerSearch: OfferSearch[_ <: Animal], count: Int): Future[List[OfferModel]] = {
    val selector = getOfferSearchFindQuery(offerSearch)
    val projection = BSONDocument(
        OfferConstants.OfferAnimalList + ".$" -> 1)
    collection.flatMap(_.find(selector, projection).cursor[OfferModel]().collect[List](count, Cursor.FailOnError[List[OfferModel]]()))
//    val selector = getOfferSearchFindQuery(offerSearch)
//    collection.flatMap(col => {
//      import col.BatchCommands.AggregationFramework.{
//        AggregationResult, Group, Match, PipelineOperator, SumField, UnwindField
//      }
//      col.aggregate(
//          Match(selector), 
//          List(
//              UnwindField(MongoConstants.Data + "." + OfferConstants.OfferAnimalList),
//              Match(BSONDocument(
//                  MongoConstants.Data + "." + OfferConstants.OfferAnimalList + "." + OfferAnimalConstants.Age -> BSONDocument(
//                      "$gte" -> offerSearch.ageBoundaries.lower,
//                      "$lt" -> offerSearch.ageBoundaries.higher)
//                  )
//              ),
//              PipelineOperator(BSONDocument(
//                  "$facet" -> BSONDocument(
//                      
//          )
//      )
//    })
//    Future{ List()}
    //Future { List() }
  }
  
  private def getOfferSearchFindQuery(offerSearch: OfferSearch[_ <: Animal]): BSONDocument = {
    
    var animalDoc = BSONDocument()
    if(offerSearch.animalType.isDefined)
      animalDoc = animalDoc ++ 
      (OfferAnimalConstants.AnimalTypeDetails + "." + AnimalTypeDetailsConstants.AnimalType -> 
          animalTypeWriter.write(offerSearch.animalType.get))
          
    if(offerSearch.specificTypeSet.size>0)
      animalDoc = animalDoc ++
      (OfferAnimalConstants.AnimalTypeDetails + "." + AnimalTypeDetailsConstants.SpecificTypeSet ->
          specificTypeWriter.writeCollection(offerSearch.specificTypeSet))
          
    if(offerSearch.gender.isDefined)
      animalDoc = animalDoc ++
      (OfferAnimalConstants.Gender -> offerSearch.gender)
      
    if(offerSearch.colorSet.size>0)
      animalDoc = animalDoc ++
      (OfferAnimalConstants.ColorSet -> BSONDocument(
          "$in" -> offerSearch.colorSet))
      
    if(offerSearch.sizeSet.size>0)
      animalDoc = animalDoc ++
      (OfferAnimalConstants.Size -> BSONDocument(
          "$in" -> offerSearch.sizeSet))
          
    if(offerSearch.tagSet.size>0)
      animalDoc = animalDoc ++
      (OfferAnimalConstants.TagSet -> BSONDocument(
          "$in" -> offerSearch.tagSet))
          
    animalDoc = animalDoc ++
    (OfferAnimalConstants.Age -> BSONDocument(
        "$gte" -> offerSearch.ageBoundaries.lower,
        "$lte" -> offerSearch.ageBoundaries.higher))
        
    if(offerSearch.castratedOnly)
      animalDoc = animalDoc ++
      (OfferAnimalConstants.IsCastrated -> true)
      
    var doc = BSONDocument()
    
    if(offerSearch.countrySet.size>0)
      doc = doc ++
      (OfferConstants.Location + "." + LocationConstants.Country -> BSONDocument(
          "$in" -> offerSearch.countrySet))
          
    if(offerSearch.citySet.size>0)
      doc = doc ++
      (OfferConstants.Location + "." + LocationConstants.City -> BSONDocument(
          "$in" -> offerSearch.citySet))
          
    doc = doc ++ 
        (OfferConstants.OfferAnimalList -> BSONDocument(
            "$elemMatch" -> animalDoc))
      
    doc
  }
  
  override def addSpecificTypeById(id: String, offerAnimalId: String, specificType: SpecificType[_ <: Animal]): Future[Int] = {
    val animal = specificType.getAsAnimal
    val animalType = AnimalType.valueOf(animal)
    val selector = BSONDocument(
        MongoConstants.MongoId -> id,
        OfferConstants.OfferAnimalList -> BSONDocument(
            "$elemMatch" -> BSONDocument(
                MongoConstants.MongoId -> offerAnimalId)),
        OfferConstants.OfferAnimalList + ".$."
          + OfferAnimalConstants.AnimalTypeDetails + "." + AnimalTypeDetailsConstants.AnimalType -> animalType)
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
                MongoConstants.MongoId -> offerAnimalId)),
        OfferConstants.OfferAnimalList + ".$."
          + OfferAnimalConstants.AnimalTypeDetails + "." + AnimalTypeDetailsConstants.AnimalType -> animalType)
    val update = BSONDocument(
        "$pull" -> BSONDocument(
            OfferConstants.OfferAnimalList + ".$." + OfferAnimalConstants.AnimalTypeDetails + "." + AnimalTypeDetailsConstants.SpecificTypeSet -> specificTypeWriter.write(specificType)))
    collection.flatMap(_.update(selector, update)).map((wRes) => wRes.nModified)
  }
  
}