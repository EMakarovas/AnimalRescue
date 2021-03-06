package com.emakarovas.animalrescue.persistence.dao.impl

import scala.concurrent.Future

import com.emakarovas.animalrescue.model.SearchModel
import com.emakarovas.animalrescue.model.constants.AnimalTypeDetailsConstants
import com.emakarovas.animalrescue.model.constants.LocationConstants
import com.emakarovas.animalrescue.model.constants.OfferAnimalConstants
import com.emakarovas.animalrescue.model.constants.OfferConstants
import com.emakarovas.animalrescue.model.constants.SearchAnimalConstants
import com.emakarovas.animalrescue.model.constants.SearchConstants
import com.emakarovas.animalrescue.model.enumeration.Animal
import com.emakarovas.animalrescue.model.enumeration.AnimalType
import com.emakarovas.animalrescue.model.enumeration.SpecificType
import com.emakarovas.animalrescue.persistence.dao.SearchDAO
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionType
import com.emakarovas.animalrescue.persistence.reader.SearchReader
import com.emakarovas.animalrescue.persistence.writer.SearchWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.AnimalTypeWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.SpecificTypeWriter
import com.emakarovas.animalrescue.persistence.writer.property.SearchPropertyWriter
import com.emakarovas.animalrescue.util.generator.StringGenerator

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.api.Cursor
import reactivemongo.api.indexes.IndexType
import reactivemongo.bson.BSONDocument

@Singleton
class DefaultSearchDAO @Inject() (
    val colFactory: MongoCollectionFactory,
    val stringGenerator: StringGenerator,
    implicit override val writer: SearchWriter,
    implicit override val reader: SearchReader,
    implicit override val propertyWriter: SearchPropertyWriter,
    implicit val animalTypeWriter: AnimalTypeWriter,
    implicit val specificTypeWriter: SpecificTypeWriter) extends SearchDAO {
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  val collection = colFactory.getCollection(MongoCollectionType.Search)
  
  val indexList = List(
      buildIndex(SearchConstants.Url, IndexType.Ascending, true),
      buildIndex(SearchConstants.SearchAnimalList + "." + MongoConstants.MongoId, IndexType.Ascending, true),
      buildIndex(SearchConstants.SearchAnimalList + "." + SearchAnimalConstants.Gender, IndexType.Ascending, false),
      buildIndex(SearchConstants.SearchAnimalList + "." + SearchAnimalConstants.MinAge, IndexType.Ascending, false),
      buildIndex(SearchConstants.SearchAnimalList + "." + SearchAnimalConstants.MaxAge, IndexType.Ascending, false),
      buildIndex(SearchConstants.Location + "." + LocationConstants.Country, IndexType.Ascending, false),
      buildIndex(SearchConstants.Location + "." + LocationConstants.City, IndexType.Ascending, false),
      buildIndex(SearchConstants.Location + "." + LocationConstants.Geolocation, IndexType.Geo2DSpherical, false),
      buildIndex(SearchConstants.UserId, IndexType.Ascending, false)
  )
  setUpIndexes(indexList)
 
  override def findByUserId(userId: String): Future[List[SearchModel]] = {
    val selector = BSONDocument(SearchConstants.UserId -> userId)
    collection.flatMap(_.find(selector)
        .cursor[SearchModel]()
        .collect[List](Int.MaxValue, Cursor.FailOnError[List[SearchModel]]()))
  }
  
  override def addToPotentialAnimalIdListBySearchAnimalId(id: String, searchAnimalId: String, potentialAnimalId: String): Future[Int] = {
    val selector = BSONDocument(
        MongoConstants.MongoId -> id,
        SearchConstants.SearchAnimalList -> BSONDocument(
             "$elemMatch" -> BSONDocument(
                 MongoConstants.MongoId -> searchAnimalId)))
    val update = BSONDocument("$addToSet" -> BSONDocument(
        SearchConstants.SearchAnimalList + ".$." + SearchAnimalConstants.PotentialAnimalIdList -> potentialAnimalId))
    collection.flatMap(_.update(selector, update)).map(writeRes => writeRes.nModified)
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