package com.emakarovas.animalrescue.persistence.writer.enumeration

import com.emakarovas.animalrescue.model.enumeration.Size

import javax.inject.Singleton
import reactivemongo.bson.BSONString

@Singleton
class SizeWriter extends EnumerationWriter[Size] {
  override def write(gender: Size) = BSONString(gender.toString)
}