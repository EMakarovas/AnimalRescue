package com.emakarovas.animalrescue.model.enumeration

sealed trait CostType extends ModelEnumeration[CostType]
object CostType extends ModelEnumeration[CostType] {
  case object Vaccination extends CostType
  case object Shelter extends CostType
  case object Food extends CostType
}
  