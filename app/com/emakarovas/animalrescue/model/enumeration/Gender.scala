package com.emakarovas.animalrescue.model.enumeration

sealed trait Gender extends Enum[Gender]
object Gender extends Enum[Gender] {
  case object Male extends Gender
  case object Female extends Gender
  case object Unspecified extends Gender
}