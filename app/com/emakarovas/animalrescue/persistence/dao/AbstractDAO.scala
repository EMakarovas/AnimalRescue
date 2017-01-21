package com.emakarovas.animalrescue.persistence.dao

import scala.concurrent.Future

trait AbstractDAO[T] {
  def create(obj: T): Future[Int]
}