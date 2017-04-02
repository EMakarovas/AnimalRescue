package com.emakarovas.animalrescue.persistence.writer.enumeration

import com.emakarovas.animalrescue.model.enumeration.Animal
import com.emakarovas.animalrescue.model.enumeration.AnimalType

import javax.inject.Singleton
import reactivemongo.bson.BSONString

@Singleton
class AnimalTypeWriter extends EnumerationWriter[AnimalType[_ <: Animal]] {
  override def write(animal: AnimalType[_ <: Animal]) = BSONString(animal.toString)
}