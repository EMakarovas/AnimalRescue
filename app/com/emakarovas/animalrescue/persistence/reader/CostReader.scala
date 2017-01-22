package com.emakarovas.animalrescue.persistence.reader

import com.emakarovas.animalrescue.model.CostModel
import com.emakarovas.animalrescue.model.cost.CostType
import com.emakarovas.animalrescue.persistence.reader.enumeration.CostTypeReader

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class CostReader @Inject() 
  (implicit costTypeReader: CostTypeReader) extends AbstractModelReader[CostModel] {
  
  def read(doc: BSONDocument): CostModel = {
    val id = doc.getAs[String]("_id").get
    val costType = doc.getAs[CostType.Value]("costType").get
    val amount = doc.getAs[Double]("amount").get
    CostModel(id, costType, amount)
  }
}

