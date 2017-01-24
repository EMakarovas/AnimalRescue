package com.emakarovas.animalrescue.persistence.writer.enumeration

import com.emakarovas.animalrescue.model.enumeration.ModelEnumeration

import reactivemongo.bson.BSONString
import reactivemongo.bson.BSONWriter

import javax.inject.Singleton
import com.emakarovas.animalrescue.model.enumeration.Gender

trait EnumerationWriter[T <: ModelEnumeration[T]] extends BSONWriter[T, BSONString] {
  def write(enum: T): BSONString
}