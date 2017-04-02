package com.emakarovas.animalrescue.persistence.reader.enumeration

import com.emakarovas.animalrescue.model.enumeration.Color

import javax.inject.Singleton
import reactivemongo.bson.BSONString
import reactivemongo.bson.BSONValue

@Singleton
class ColorReader extends EnumerationReader[Color] {
  override def read(bson: BSONValue): Color =
    bson match {
      case BSONString(str) => Color.valueOf(str)
      case _ => throw new IllegalBSONValueException()
    }
}