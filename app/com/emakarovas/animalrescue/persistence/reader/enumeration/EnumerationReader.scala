package com.emakarovas.animalrescue.persistence.reader.enumeration

import com.emakarovas.animalrescue.model.enumeration.ModelEnumeration

import reactivemongo.bson.BSONReader
import reactivemongo.bson.BSONValue

trait EnumerationReader[T <: ModelEnumeration[T]] extends BSONReader[BSONValue, T] {
  override def read(bson: BSONValue): T
}