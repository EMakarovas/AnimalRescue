package com.emakarovas.animalrescue.persistence.dao

import scala.concurrent.Future

import com.emakarovas.animalrescue.model.AbstractModel
import reactivemongo.api.commands.WriteResult

trait AbstractDAO[T<:AbstractModel] {
  def findById(id: String): Future[Option[T]]
  def findAll(): Future[List[T]]
  def create(model: T): Future[Int]
  def update(model: T): Future[Int]
  def deleteById(id: String): Future[Int]
}