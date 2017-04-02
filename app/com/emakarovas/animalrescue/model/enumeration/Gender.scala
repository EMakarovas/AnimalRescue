package com.emakarovas.animalrescue.model.enumeration

sealed trait Gender extends Enum[Gender]
object Gender extends Enum[Gender] {
  case object Male extends Gender
  case object Female extends Gender
  
  def valueOf(value: String) = value match {
    case "Male" => Male
    case "Female" => Female
    case _ => throw new EnumerationNotFoundException()
  }
  
}