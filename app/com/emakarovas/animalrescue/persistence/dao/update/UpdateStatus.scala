package com.emakarovas.animalrescue.persistence.dao.update

sealed trait UpdateStatus
object UpdateStatus {
  /**
   * Returned when an update token was correct and the update executed successfully
   */
  case object Executed extends UpdateStatus
  /**
   * Returned when the update token was not valid
   */
  case object Denied extends UpdateStatus
}
  