package com.emakarovas.animalrescue.model.enumeration

sealed trait Size extends Enum[Size]
object Size extends Enum[Size] {
  case object Small extends Size
  case object Medium extends Size
  case object Large extends Size
  
  def valueOf(value: String) = value match {
    case "Small" => Small
    case "Medium" => Medium
    case "Large" => Large
    case _ => throw new EnumerationNotFoundException()
  }
  
}