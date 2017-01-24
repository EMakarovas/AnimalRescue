package com.emakarovas.animalrescue.model

import com.emakarovas.animalrescue.model.enumeration.CostType

case class CostModel
  (override val id: String,
   costType: CostType,
   amount: Double)
   extends AbstractModel(id) {
  
}