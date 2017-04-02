package com.emakarovas.animalrescue.model.enumeration

sealed trait Color extends Enum[Color]
object Color extends Enum[Color] {
  case object Black extends Color
  case object Blue extends Color
  case object Brown extends Color
  case object Golden extends Color
  case object Green extends Color
  case object Orange extends Color
  case object Pink extends Color
  case object Red extends Color
  case object White extends Color
  case object Yellow extends Color
  
  def valueOf(value: String) = value match {
    case "Black" => Black
    case "Blue" => Blue
    case "Brown" => Brown
    case "Golden" => Golden
    case "Green" => Green
    case "Orange" => Orange
    case "Pink" => Pink
    case "Red" => Red
    case "White" => White
    case "Yellow" => Yellow
    case _ => throw new EnumerationNotFoundException()
  }
  
}