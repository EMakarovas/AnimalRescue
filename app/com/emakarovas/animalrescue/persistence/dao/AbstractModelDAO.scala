package com.emakarovas.animalrescue.persistence.dao

import scala.collection.mutable.ListBuffer
import scala.concurrent.Future

import com.emakarovas.animalrescue.model.AbstractModel
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants

import reactivemongo.api.Cursor
import reactivemongo.bson.BSONDocument
import reactivemongo.api.indexes.IndexType
import reactivemongo.api.indexes.Index

trait AbstractModelDAO[T <: AbstractModel] extends AbstractDAO[T] {
  
  import scala.concurrent.ExecutionContext.Implicits.global
    
  def findById(id: String): Future[Option[T]] = {
    val query = BSONDocument(MongoConstants.MongoId -> id)
    collection.flatMap(_.find(query).one)
  }
  
  def findAll(count: Int): Future[List[T]] = {
    collection.flatMap(_.find(BSONDocument()).cursor[T]().collect[List](count, Cursor.FailOnError[List[T]]()))
  }
  
  def create(obj: T): Future[Int] = {
    collection.flatMap(_.insert(obj)).map(writeRes => writeRes.n)
  }
  
  def deleteById(id: String): Future[Int] = {
    val selector = BSONDocument(MongoConstants.MongoId -> id)
    collection.flatMap(_.remove(selector)).map(writeRes => writeRes.n)
  }
  
  def buildIndex(name: String, iType: IndexType, unique: Boolean): Index = {
    val key = Seq((name, iType))
    Index(key, Some(name), unique)
  }
  
  protected def setUpIndexes(indexList: Seq[Index]): Unit = {
    for(index <- indexList)
      setIndex(index)
  }
  
  protected def setIndex(index: Index): Future[Boolean] = {
    collection.flatMap(_.indexesManager.ensure(index))
  }
  
}