package com.emakarovas.animalrescue.persistence.dao

import scala.concurrent.Future

import reactivemongo.api.Cursor
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.BSONDocument
import reactivemongo.bson.BSONDocumentReader
import reactivemongo.bson.BSONDocumentWriter

trait AbstractDAO[T] {
  
  val collection: Future[BSONCollection]
  implicit def writer: BSONDocumentWriter[T]
  implicit def reader: BSONDocumentReader[T]
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  def findById(id: String): Future[Option[T]] = {
    val query = BSONDocument("_id" -> id)
    collection.flatMap(_.find(query).one)
  }
  
  def findAll(): Future[List[T]] = {
    collection.flatMap(_.find(BSONDocument()).cursor[T]().collect[List](Int.MaxValue, Cursor.FailOnError[List[T]]()))
  }
  
  def create(obj: T): Future[Int] = {
    collection.flatMap(_.insert(obj)).map(writeRes => writeRes.n)
  }
  
  def deleteById(id: String): Future[Int] = {
    val selector = BSONDocument("_id" -> id)
    collection.flatMap(_.remove(selector)).map(writeRes => writeRes.n)
  }
}