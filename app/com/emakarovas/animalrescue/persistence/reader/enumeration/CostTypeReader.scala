package com.emakarovas.animalrescue.persistence.reader.enumeration

import com.emakarovas.animalrescue.model.cost.CostType

import javax.inject.Singleton
import reactivemongo.bson.BSONReader
import reactivemongo.bson.BSONString
import reactivemongo.bson.BSONValue

@Singleton
class CostTypeReader extends BSONReader[BSONValue, CostType.Value] {
  def read(bson: BSONValue): CostType.Value =
    bson match {
      case BSONString("Vaccination") => CostType.Vaccination
      case BSONString("Shelter") => CostType.Shelter
      case BSONString("Food") => CostType.Food
      case _ => throw new EnumerationNotFoundException
    }
}