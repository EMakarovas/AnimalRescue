package com.emakarovas.animalrescue.model.enumeration

sealed trait Gender extends ModelEnumeration[Gender]
object Gender extends ModelEnumeration[Gender] {
  case object Male extends Gender
  case object Female extends Gender
  case object Unspecified extends Gender
}
  