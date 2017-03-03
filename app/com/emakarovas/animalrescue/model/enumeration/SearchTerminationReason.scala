package com.emakarovas.animalrescue.model.enumeration

sealed trait SearchTerminationReason extends ModelEnumeration[SearchTerminationReason]
object SearchTerminationReason extends ModelEnumeration[SearchTerminationReason] {
  case object FoundHere extends SearchTerminationReason
  case object FoundElsewhere extends SearchTerminationReason
  case object StoppedLooking extends SearchTerminationReason
  case object Other extends SearchTerminationReason
}
  