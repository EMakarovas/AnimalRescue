package com.emakarovas.animalrescue.testutil

import org.scalatestplus.play.PlaySpec

trait DelayedPlaySpec extends PlaySpec {
  
  def delay() = Thread.sleep(100) // If not called, the tests sometimes overlap
}