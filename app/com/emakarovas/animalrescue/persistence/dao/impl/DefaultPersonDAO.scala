package com.emakarovas.animalrescue.persistence.dao.impl

import com.emakarovas.animalrescue.persistence.dao.PersonDAO
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.mongo.PersonModelCollectionType
import com.emakarovas.animalrescue.persistence.reader.PersonReader
import com.emakarovas.animalrescue.persistence.writer.PersonWriter

import javax.inject.Inject
import javax.inject.Singleton
import scala.concurrent.Future
import com.emakarovas.animalrescue.model.PersonModel
import reactivemongo.bson.BSONDocument

@Singleton
class DefaultPersonDAO @Inject() (
    colFactory: MongoCollectionFactory,
    implicit val writer: PersonWriter,
    implicit val reader: PersonReader) extends PersonDAO {
  
  val collection = colFactory.getCollection(PersonModelCollectionType)
  
  import scala.concurrent.ExecutionContext.Implicits.global
 
  override def findByUserId(userId: String): Future[Option[PersonModel]] = {
    val selector = BSONDocument("userId" -> userId)
    collection.flatMap(_.find(selector).one)
  }
  
}