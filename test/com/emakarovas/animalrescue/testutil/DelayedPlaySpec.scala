package com.emakarovas.animalrescue.testutil

import org.scalatestplus.play.PlaySpec

trait DelayedPlaySpec extends PlaySpec {
  // TODO this doesn't really work, need to change
  def delay() = Thread.sleep(10) // If not called, the tests sometimes overlap}
}