package com.emakarovas.animalrescue.persistence.reader.enumeration

import com.emakarovas.animalrescue.model.enumeration.Size

import javax.inject.Singleton
import reactivemongo.bson.BSONString
import reactivemongo.bson.BSONValue

@Singleton
class SizeReader extends EnumerationReader[Size] {
  override def read(bson: BSONValue): Size =
    bson match {
      case BSONString(str) => Size.valueOf(str)
      case _ => throw new IllegalBSONValueException()
    }
}