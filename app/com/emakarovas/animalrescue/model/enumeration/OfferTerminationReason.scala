package com.emakarovas.animalrescue.model.enumeration

sealed trait OfferTerminationReason extends ModelEnumeration[OfferTerminationReason]
object OfferTerminationReason extends ModelEnumeration[OfferTerminationReason] {
  case object GaveToUser extends OfferTerminationReason
  case object GaveToSomeoneElse extends OfferTerminationReason
  case object KeptIt extends OfferTerminationReason
  case object Other extends OfferTerminationReason
}
  