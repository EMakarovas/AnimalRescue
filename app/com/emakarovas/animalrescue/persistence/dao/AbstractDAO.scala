package com.emakarovas.animalrescue.persistence.dao

import scala.concurrent.Future
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.BSONDocumentWriter
import reactivemongo.bson.BSONDocumentReader
import com.emakarovas.animalrescue.model.PersonModel
import reactivemongo.bson.BSONDocument

trait AbstractDAO[T] {
  
  val collection: Future[BSONCollection]
  implicit val writer: BSONDocumentWriter[T]
  implicit val reader: BSONDocumentReader[T]
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  def findById(id: String): Future[Option[T]] = {
    val query = BSONDocument("_id" -> id)
    collection.flatMap(_.find(query).one)
  }
  
  def findAll(): Future[List[T]] = collection.flatMap(_.find(BSONDocument(), BSONDocument()).cursor[T]().collect[List]())
  
  def create(obj: T): Future[Int] = collection.flatMap(_.insert(obj)).map(writeRes => writeRes.n)
  
  def deleteById(id: String): Future[Int] = {
    val selector = BSONDocument("_id" -> id)
    collection.flatMap(_.remove(selector)).map(writeRes => writeRes.n)
  }
}