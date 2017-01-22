package com.emakarovas.animalrescue.persistence.reader.enumeration

import com.emakarovas.animalrescue.model.common.Gender

import javax.inject.Singleton
import reactivemongo.bson.BSONReader
import reactivemongo.bson.BSONString
import reactivemongo.bson.BSONValue

@Singleton
class GenderReader extends BSONReader[BSONValue, Gender.Value] {
  def read(bson: BSONValue): Gender.Value =
    bson match {
      case BSONString("Female") => Gender.Female
      case BSONString("Male") => Gender.Male
      case BSONString("Unspecified") => Gender.Unspecified
      case _ => throw new EnumerationNotFoundException
    }
}