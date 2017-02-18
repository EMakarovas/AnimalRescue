package com.emakarovas.animalrescue.persistence.dao

import scala.concurrent.Future

import com.emakarovas.animalrescue.model.AbstractModel
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.BSONDocument
import reactivemongo.api.Cursor
import com.emakarovas.animalrescue.model.AvailableModel

trait AbstractAvailableModelDAO[T <: AvailableModel[T]] extends AbstractDAO[T] {
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  def findAllAvailable(): Future[List[T]] = {
    val selector = BSONDocument("available" -> true)
    collection.flatMap(_.find(selector).cursor[T]().collect[List](Int.MaxValue, Cursor.FailOnError[List[T]]()))
  }
  
}