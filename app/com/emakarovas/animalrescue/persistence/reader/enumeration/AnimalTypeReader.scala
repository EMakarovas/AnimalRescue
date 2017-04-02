package com.emakarovas.animalrescue.persistence.reader.enumeration

import com.emakarovas.animalrescue.model.enumeration.Animal
import com.emakarovas.animalrescue.model.enumeration.AnimalType

import javax.inject.Singleton
import reactivemongo.bson.BSONString
import reactivemongo.bson.BSONValue

@Singleton
class AnimalTypeReader extends EnumerationReader[AnimalType[_ <: Animal]] {
  def read(bson: BSONValue): AnimalType[_ <: Animal] = bson match {
    case BSONString(str) => AnimalType.valueOf(str)
    case _ => throw new IllegalBSONValueException()
  }
}