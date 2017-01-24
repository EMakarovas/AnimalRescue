package com.emakarovas.animalrescue.persistence.writer.enumeration

import reactivemongo.bson.BSONString
import reactivemongo.bson.BSONWriter

import javax.inject.Singleton
import com.emakarovas.animalrescue.model.enumeration.AnimalType

@Singleton
class AnimalTypeWriter extends EnumerationWriter[AnimalType] {
  override def write(animal: AnimalType) = BSONString(animal.toString)
}