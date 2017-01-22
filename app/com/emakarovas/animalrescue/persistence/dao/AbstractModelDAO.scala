package com.emakarovas.animalrescue.persistence.dao

import scala.concurrent.Future

import com.emakarovas.animalrescue.model.AbstractModel
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.BSONDocument

trait AbstractModelDAO[T <: AbstractModel] extends AbstractDAO[T] {
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  def update(model: T): Future[Int] = {
    val selector = BSONDocument("_id" -> model.id)
    collection.flatMap(_.update(selector, model)).map(writeRes => writeRes.n)
  }
  
}