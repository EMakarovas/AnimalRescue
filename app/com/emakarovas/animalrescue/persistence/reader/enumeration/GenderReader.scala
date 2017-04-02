package com.emakarovas.animalrescue.persistence.reader.enumeration

import com.emakarovas.animalrescue.model.enumeration.Gender

import javax.inject.Singleton
import reactivemongo.bson.BSONString
import reactivemongo.bson.BSONValue

@Singleton
class GenderReader extends EnumerationReader[Gender] {
  override def read(bson: BSONValue): Gender =
    bson match {
      case BSONString(str) => Gender.valueOf(str)
      case _ => throw new IllegalBSONValueException()
    }
}