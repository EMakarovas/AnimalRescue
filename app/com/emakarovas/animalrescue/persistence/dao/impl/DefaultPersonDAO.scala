package com.emakarovas.animalrescue.persistence.dao.impl

import com.emakarovas.animalrescue.model.PersonModel
import com.emakarovas.animalrescue.persistence.dao.PersonDAO
import com.emakarovas.animalrescue.persistence.mongo.Mongo
import com.emakarovas.animalrescue.persistence.writer.PersonWriter
import com.emakarovas.animalrescue.persistence.reader.PersonReader

import javax.inject._
import reactivemongo.bson.BSONDocument
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.mongo.PersonModelCollectionType

@Singleton
class DefaultPersonDAO @Inject() (
    colFactory: MongoCollectionFactory,
    implicit val writer: PersonWriter,
    implicit val reader: PersonReader) extends PersonDAO {
  
  import scala.concurrent.ExecutionContext.Implicits.global
  val collection = colFactory.getCollection(PersonModelCollectionType)
      
  override def update(person: PersonModel) = {
    val selector = BSONDocument("_id" -> person.id)
    collection.flatMap(_.update(selector, person)).map(writeRes => writeRes.n)
  }
  
}