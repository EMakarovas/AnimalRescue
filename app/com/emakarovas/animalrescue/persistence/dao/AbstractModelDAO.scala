package com.emakarovas.animalrescue.persistence.dao

import scala.collection.mutable.ListBuffer
import scala.concurrent.Future

import com.emakarovas.animalrescue.model.AbstractModel
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants

import reactivemongo.api.Cursor
import reactivemongo.bson.BSONDocument

trait AbstractModelDAO[T <: AbstractModel] extends AbstractDAO[T] {
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  protected val createList: ListBuffer[T] = ListBuffer.empty[T]
  
  def findById(id: String): Future[Option[T]] = {
    val filteredList = createList.filter((obj) => obj.id==id)
    if(filteredList.size==1)
      return Future { Some(filteredList(0)) }
    val query = BSONDocument(MongoConstants.MongoId -> id)
    collection.flatMap(_.find(query).one)
  }
  
  def findAll(): Future[List[T]] = {
    val f = collection.flatMap(_.find(BSONDocument()).cursor[T]().collect[List](Int.MaxValue, Cursor.FailOnError[List[T]]()))
    f.map((list) => (list ++ createList).distinct)
  }
  
  def create(obj: T): Future[Int] = {
    createList += obj
    val f = collection.flatMap(_.insert(obj))
    f onSuccess {
      case _ => createList -= obj
    }
    f.map(writeRes => writeRes.n)
  }
  
  def deleteById(id: String): Future[Int] = {
    val selector = BSONDocument(MongoConstants.MongoId -> id)
    collection.flatMap(_.remove(selector)).map(writeRes => writeRes.n)
  }
  
}