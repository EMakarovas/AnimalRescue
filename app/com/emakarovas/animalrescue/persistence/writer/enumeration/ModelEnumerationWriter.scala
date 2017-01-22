package com.emakarovas.animalrescue.persistence.writer.enumeration

import com.emakarovas.animalrescue.model.common.ModelEnumeration

import reactivemongo.bson.BSONString
import reactivemongo.bson.BSONWriter

import javax.inject.Singleton

@Singleton
class ModelEnumerationWriter extends BSONWriter[ModelEnumeration, BSONString] {
  def write(t: ModelEnumeration): BSONString = BSONString(t.toString())
}