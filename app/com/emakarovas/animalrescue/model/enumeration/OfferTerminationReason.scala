package com.emakarovas.animalrescue.model.enumeration

sealed trait OfferTerminationReason extends Enum[OfferTerminationReason]
object OfferTerminationReason extends Enum[OfferTerminationReason] {
  case object GaveToUser extends OfferTerminationReason
  case object GaveToSomeoneElse extends OfferTerminationReason
  case object KeptIt extends OfferTerminationReason
  case object Other extends OfferTerminationReason
  
  def valueOf(value: String) = value match {
    case "GaveToUser" => GaveToUser
    case "GaveToSomeoneElse" => GaveToSomeoneElse
    case "KeptIt" => KeptIt
    case "Other" => Other
    case _ => throw new EnumerationNotFoundException()
  }
  
}
  