package com.emakarovas.animalrescue.persistence.dao.impl

import com.emakarovas.animalrescue.model.PersonModel
import com.emakarovas.animalrescue.persistence.dao.PersonDAO
import com.emakarovas.animalrescue.persistence.mongo.Mongo
import com.emakarovas.animalrescue.persistence.writer.PersonWriter

import javax.inject._
import reactivemongo.bson.BSONDocument
import com.emakarovas.animalrescue.persistence.reader.PersonReader
import play.api.libs.json.Json
import reactivemongo.bson.BSONDocumentWriter
import play.api.libs.json.JsObject
import reactivemongo.bson.Macros
import reactivemongo.api.Cursor


@Singleton
class DefaultPersonDAO @Inject() (
    val mongo: Mongo, 
    implicit val personWriter: PersonWriter,
    implicit val personReader: PersonReader) extends PersonDAO {
  
  import scala.concurrent.ExecutionContext.Implicits.global
  val collection = mongo.db.map(_.collection("person"))
  
  override def findById(id: String) = {
    val query = BSONDocument("_id" -> id)
    collection.flatMap(_.find(query).one)
  }
  
  override def findAll() = collection.flatMap(_.find(BSONDocument(), BSONDocument()).cursor[PersonModel]().collect[List]())
  
  override def create(person: PersonModel) = collection.flatMap(_.insert(person)).map(writeRes => writeRes.n)
  
  override def update(person: PersonModel) = {
    val selector = BSONDocument("_id" -> person.id)
    collection.flatMap(_.update(selector, person)).map(writeRes => writeRes.n)
  }
  
  override def deleteById(id: String) = {
    val selector = BSONDocument("_id" -> id)
    collection.flatMap(_.remove(selector)).map(writeRes => writeRes.n)
  }
  
}