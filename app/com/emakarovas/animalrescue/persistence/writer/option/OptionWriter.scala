package com.emakarovas.animalrescue.persistence.writer.option

import reactivemongo.bson.BSONValue
import reactivemongo.bson.BSONWriter

trait OptionWriter[T] extends BSONWriter[Option[T], BSONValue] {
  override def write(option: Option[T]): BSONValue
}