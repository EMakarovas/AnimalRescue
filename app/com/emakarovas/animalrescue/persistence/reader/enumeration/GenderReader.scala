package com.emakarovas.animalrescue.persistence.reader.enumeration

import com.emakarovas.animalrescue.model.enumeration.Gender

import javax.inject.Singleton
import reactivemongo.bson.BSONString
import reactivemongo.bson.BSONValue

@Singleton
class GenderReader extends EnumerationReader[Gender] {
  override def read(bson: BSONValue): Gender =
    bson match {
      case BSONString("Female") => Gender.Female
      case BSONString("Male") => Gender.Male
      case BSONString("Unspecified") => Gender.Unspecified
      case _ => throw new EnumerationNotFoundException
    }
}