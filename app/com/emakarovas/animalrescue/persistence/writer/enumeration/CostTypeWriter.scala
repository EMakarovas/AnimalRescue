package com.emakarovas.animalrescue.persistence.writer.enumeration

import reactivemongo.bson.BSONString
import reactivemongo.bson.BSONWriter

import javax.inject.Singleton
import com.emakarovas.animalrescue.model.enumeration.CostType

@Singleton
class CostTypeWriter extends EnumerationWriter[CostType] {
  override def write(gender: CostType) = BSONString(gender.toString)
}