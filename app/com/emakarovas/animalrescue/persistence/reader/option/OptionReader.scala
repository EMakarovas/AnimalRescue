package com.emakarovas.animalrescue.persistence.reader.option

import reactivemongo.bson.BSONReader
import reactivemongo.bson.BSONValue

trait OptionReader[T] extends BSONReader[BSONValue, Option[T]] {
  override def read(value: BSONValue): Option[T]
}