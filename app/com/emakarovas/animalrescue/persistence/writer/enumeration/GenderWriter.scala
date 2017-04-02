package com.emakarovas.animalrescue.persistence.writer.enumeration

import com.emakarovas.animalrescue.model.enumeration.Gender

import javax.inject.Singleton
import reactivemongo.bson.BSONString

@Singleton
class GenderWriter extends EnumerationWriter[Gender] {
  override def write(gender: Gender) = BSONString(gender.toString)
}