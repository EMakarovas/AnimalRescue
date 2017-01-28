package com.emakarovas.animalrescue.persistence.reader.option

import reactivemongo.bson.BSONReader
import reactivemongo.bson.BSONValue

trait OptionReader[T] extends BSONReader[BSONValue, Option[T]] {
  
  implicit def reader: BSONReader[BSONValue, T] = new BSONReader[BSONValue, T] {
    def read(v: BSONValue): T = v.as[T]
  }
  
  override def read(value: BSONValue): Option[T]
  
}