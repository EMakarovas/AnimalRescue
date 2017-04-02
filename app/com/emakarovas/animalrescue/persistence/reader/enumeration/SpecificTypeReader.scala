package com.emakarovas.animalrescue.persistence.reader.enumeration

import com.emakarovas.animalrescue.model.enumeration.Animal
import com.emakarovas.animalrescue.model.enumeration.SpecificType

import javax.inject.Singleton
import reactivemongo.bson.BSONString
import reactivemongo.bson.BSONValue

@Singleton
class SpecificTypeReader extends EnumerationReader[SpecificType[Animal]] {
  override def read(bson: BSONValue): SpecificType[Animal] =
    bson match {
      case BSONString(str) => SpecificType.valueOf(str)
      case _ => throw new IllegalBSONValueException()
    }
}