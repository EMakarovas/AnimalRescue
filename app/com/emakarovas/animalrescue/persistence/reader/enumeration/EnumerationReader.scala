package com.emakarovas.animalrescue.persistence.reader.enumeration

import com.emakarovas.animalrescue.model.enumeration.Enum

import reactivemongo.bson.BSONReader
import reactivemongo.bson.BSONValue

trait EnumerationReader[T <: Enum[T]] extends BSONReader[BSONValue, T] {
  override def read(bson: BSONValue): T
}