package com.emakarovas.animalrescue.persistence.writer.enumeration

import com.emakarovas.animalrescue.model.enumeration.Enum

import reactivemongo.bson.BSONString
import reactivemongo.bson.BSONWriter

trait EnumerationWriter[T <: Enum[T]] extends BSONWriter[T, BSONString] {
  override def write(enum: T): BSONString
}