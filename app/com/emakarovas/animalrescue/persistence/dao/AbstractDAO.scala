package com.emakarovas.animalrescue.persistence.dao

import scala.concurrent.Future

import com.emakarovas.animalrescue.model.AbstractModel
import reactivemongo.api.commands.WriteResult

trait AbstractDAO[T<:AbstractModel] {
  def findById(id: String): Future[Option[T]]
  def findAll(): Future[List[T]]
  def create(model: T): Future[WriteResult]
  def update(model: T): Future[WriteResult]
  def delete(model: T): Future[WriteResult]
}