package com.emakarovas.animalrescue.persistence.dao.impl

import scala.concurrent.Future

import com.emakarovas.animalrescue.model.AnimalModel
import com.emakarovas.animalrescue.model.constants.AdoptionDetailsConstants
import com.emakarovas.animalrescue.model.constants.AnimalConstants
import com.emakarovas.animalrescue.model.constants.AnimalTypeDetailsConstants
import com.emakarovas.animalrescue.model.enumeration.Animal
import com.emakarovas.animalrescue.model.enumeration.AnimalType
import com.emakarovas.animalrescue.model.enumeration.SpecificType
import com.emakarovas.animalrescue.persistence.dao.AnimalDAO
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionType
import com.emakarovas.animalrescue.persistence.reader.AnimalReader
import com.emakarovas.animalrescue.persistence.writer.AnimalWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.AnimalTypeWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.SpecificTypeWriter
import com.emakarovas.animalrescue.persistence.writer.property.AnimalPropertyWriter
import com.emakarovas.animalrescue.util.generator.StringGenerator

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.api.Cursor
import reactivemongo.api.indexes.IndexType
import reactivemongo.bson.BSONDocument

@Singleton
class DefaultAnimalDAO @Inject() (
    val colFactory: MongoCollectionFactory,
    val stringGenerator: StringGenerator,
    implicit override val writer: AnimalWriter,
    implicit override val reader: AnimalReader,
    implicit override val propertyWriter: AnimalPropertyWriter,
    implicit val animalTypeWriter: AnimalTypeWriter,
    implicit val specificTypeWriter: SpecificTypeWriter) extends AnimalDAO {
  
  val collection = colFactory.getCollection(MongoCollectionType.Animal)
 
  import scala.concurrent.ExecutionContext.Implicits.global
  
  private val indexList = List(
      buildIndex(AnimalConstants.AnimalTypeDetails + "." + AnimalTypeDetailsConstants.AnimalType, IndexType.Ascending, false),
      buildIndex(AnimalConstants.AnimalTypeDetails + "." + AnimalTypeDetailsConstants.SpecificTypeSet, IndexType.Ascending, false),
      buildIndex(AnimalConstants.Gender, IndexType.Ascending, false),
      buildIndex(AnimalConstants.Age, IndexType.Ascending, false),
      buildIndex(AnimalConstants.IsCastrated, IndexType.Ascending, false),
      buildIndex(AnimalConstants.AdoptionDetails + "." + AdoptionDetailsConstants.OwnerId, IndexType.Ascending, false)
  )  
  setUpIndexes(indexList)
  
  override def findByOwnerId(ownerId: String): Future[List[AnimalModel]] = {
    val selector = BSONDocument(AnimalConstants.AdoptionDetails + "." + AdoptionDetailsConstants.OwnerId -> ownerId)
    collection.flatMap(_.find(selector)
        .cursor[AnimalModel]()
        .collect[List](Int.MaxValue, Cursor.FailOnError[List[AnimalModel]]()))
  }
  
  override def addSpecificTypeById(id: String, specificType: SpecificType[_ <: Animal]): Future[Int] = {
    val animal = specificType.getAsAnimal
    val animalType = AnimalType.valueOf(animal)
    val selector = BSONDocument(
        MongoConstants.MongoId -> id,
        AnimalConstants.AnimalTypeDetails + "." + AnimalTypeDetailsConstants.AnimalType -> animalType)
    val update = BSONDocument(
        "$addToSet" -> BSONDocument(
            AnimalConstants.AnimalTypeDetails + "." + AnimalTypeDetailsConstants.SpecificTypeSet -> specificTypeWriter.write(specificType)))
    collection.flatMap(_.update(selector, update)).map((wRes) => wRes.nModified)
  }
  
  override def removeSpecificTypeById(id: String, specificType: SpecificType[_ <: Animal]): Future[Int] = {
    val animal = specificType.getAsAnimal
    val animalType = AnimalType.valueOf(animal)
    val selector = BSONDocument(
        MongoConstants.MongoId -> id,
        AnimalConstants.AnimalTypeDetails + "." + AnimalTypeDetailsConstants.AnimalType -> animalType)
    val update = BSONDocument(
        "$pull" -> BSONDocument(
            AnimalConstants.AnimalTypeDetails + "." + AnimalTypeDetailsConstants.SpecificTypeSet -> specificTypeWriter.write(specificType)))
    collection.flatMap(_.update(selector, update)).map((wRes) => wRes.nModified)
  }

}