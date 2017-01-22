package com.emakarovas.animalrescue.model

import com.emakarovas.animalrescue.model.cost.CostType

case class CostModel
  (override val id: String,
   costType: CostType.Value,
   amount: Double)
   extends AbstractModel(id) {
  
}