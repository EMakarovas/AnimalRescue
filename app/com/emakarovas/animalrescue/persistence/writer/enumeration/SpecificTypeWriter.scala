package com.emakarovas.animalrescue.persistence.writer.enumeration

import com.emakarovas.animalrescue.model.enumeration.Animal
import com.emakarovas.animalrescue.model.enumeration.SpecificType

import javax.inject.Singleton
import reactivemongo.bson.BSONArray
import reactivemongo.bson.BSONString

@Singleton
class SpecificTypeWriter extends EnumerationWriter[SpecificType[_ <: Animal]] {
  override def write(gender: SpecificType[_ <: Animal]) = BSONString(gender.toString)
  def writeCollection(col: Traversable[SpecificType[_ <: Animal]]): BSONArray = {
    BSONArray(col.map((st) => this.write(st)).toStream)
  }
}