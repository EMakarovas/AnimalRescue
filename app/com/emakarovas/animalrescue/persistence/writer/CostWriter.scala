package com.emakarovas.animalrescue.persistence.writer

import com.emakarovas.animalrescue.model.CostModel

import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class CostWriter extends AbstractModelWriter[CostModel] {
  override def write(cost: CostModel): BSONDocument = {
    BSONDocument(
        "_id" -> cost.id,
        "costType" -> cost.costType.toString(),
        "amount" -> cost.amount)
  }
}