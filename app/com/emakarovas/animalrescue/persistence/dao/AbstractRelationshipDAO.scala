package com.emakarovas.animalrescue.persistence.dao

import scala.concurrent.Future

import com.emakarovas.animalrescue.model.relationship.AbstractRelationship

import reactivemongo.api.Cursor
import reactivemongo.bson.BSONDocument

trait AbstractRelationshipDAO[T <: AbstractRelationship[_, _]] extends AbstractDAO[T] {
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  def findBySourceId(sourceId: String): Future[List[T]] = {
    val query = BSONDocument("sourceId" -> sourceId)
    collection.flatMap(_.find(query).cursor[T]().collect[List](Int.MaxValue, Cursor.FailOnError[List[T]]()))
  }
  
  def findByTargetId(targetId: String): Future[List[T]] = {
    val query = BSONDocument("targetId" -> targetId)
    collection.flatMap(_.find(query).cursor[T]().collect[List](Int.MaxValue, Cursor.FailOnError[List[T]]()))
  }
  
}