package com.emakarovas.animalrescue.persistence.dao.impl

import scala.concurrent.Future

import com.emakarovas.animalrescue.model.AnimalModel
import com.emakarovas.animalrescue.model.constants.AdoptionDetailsConstants
import com.emakarovas.animalrescue.model.constants.AnimalConstants
import com.emakarovas.animalrescue.model.constants.OfferDetailsConstants
import com.emakarovas.animalrescue.persistence.dao.AnimalDAO
import com.emakarovas.animalrescue.persistence.mongo.Animal
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.reader.AnimalReader
import com.emakarovas.animalrescue.persistence.writer.AnimalWriter
import com.emakarovas.animalrescue.util.generator.StringGenerator

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.api.Cursor
import reactivemongo.bson.BSONDocument

@Singleton
class DefaultAnimalDAO @Inject() (
    colFactory: MongoCollectionFactory,
    val stringGenerator: StringGenerator,
    implicit val writer: AnimalWriter,
    implicit val reader: AnimalReader) extends AnimalDAO {
  
  val collection = colFactory.getCollection(Animal)
 
  import scala.concurrent.ExecutionContext.Implicits.global
  
  override def findByOfferId(offerId: String): Future[List[AnimalModel]] = {
    val selector = BSONDocument(AnimalConstants.OfferDetails + "." + OfferDetailsConstants.OfferId -> offerId)
    collection.flatMap(_.find(selector)
        .cursor[AnimalModel]()
        .collect[List](Int.MaxValue, Cursor.FailOnError[List[AnimalModel]]()))
  }
  
  override def findByOwnerId(ownerId: String): Future[List[AnimalModel]] = {
    val selector = BSONDocument(AnimalConstants.AdoptionDetails + "." + AdoptionDetailsConstants.OwnerId -> ownerId)
    collection.flatMap(_.find(selector)
        .cursor[AnimalModel]()
        .collect[List](Int.MaxValue, Cursor.FailOnError[List[AnimalModel]]()))
  }

}