package com.emakarovas.animalrescue.persistence.reader.enumeration

import com.emakarovas.animalrescue.model.enumeration.CostType

import javax.inject.Singleton
import reactivemongo.bson.BSONValue
import reactivemongo.bson.BSONString

@Singleton
class CostTypeReader extends EnumerationReader[CostType] {
  override def read(bson: BSONValue): CostType =
    bson match {
      case BSONString("Vaccination") => CostType.Vaccination
      case BSONString("Shelter") => CostType.Shelter
      case BSONString("Food") => CostType.Food
      case _ => throw new EnumerationNotFoundException
    }
}