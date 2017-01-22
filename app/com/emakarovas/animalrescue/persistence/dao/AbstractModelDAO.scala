package com.emakarovas.animalrescue.persistence.dao

import scala.concurrent.Future

import com.emakarovas.animalrescue.model.AbstractModel
import reactivemongo.api.commands.WriteResult

trait AbstractModelDAO[T <: AbstractModel] extends AbstractDAO[T] {
  def update(model: T): Future[Int]
}