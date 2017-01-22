package com.emakarovas.animalrescue.persistence.dao

import scala.concurrent.Future

trait AbstractDAO[T] {
  def findById(id: String): Future[Option[T]]
  def findAll(): Future[List[T]]
  def create(obj: T): Future[Int]
  def deleteById(id: String): Future[Int]
}