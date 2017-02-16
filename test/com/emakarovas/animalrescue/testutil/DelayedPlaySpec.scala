package com.emakarovas.animalrescue.testutil

import org.scalatestplus.play.PlaySpec

trait DelayedPlaySpec extends PlaySpec {
  
  def delay() = Thread.sleep(200) // If not called, the tests sometimes overlap
}