package com.emakarovas.animalrescue.model.cost

import com.emakarovas.animalrescue.model.common.ModelEnumeration

object CostType extends ModelEnumeration {
  type CostType = Value
  val Vaccination,
  Shelter,
  Food = Value
}