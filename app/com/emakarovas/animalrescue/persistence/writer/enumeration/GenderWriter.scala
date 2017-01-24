package com.emakarovas.animalrescue.persistence.writer.enumeration

import reactivemongo.bson.BSONString
import reactivemongo.bson.BSONWriter

import javax.inject.Singleton
import com.emakarovas.animalrescue.model.enumeration.Gender

@Singleton
class GenderWriter extends EnumerationWriter[Gender] {
  override def write(gender: Gender) = BSONString(gender.toString)
}