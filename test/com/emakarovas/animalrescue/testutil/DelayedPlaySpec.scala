package com.emakarovas.animalrescue.testutil

import org.scalatestplus.play.PlaySpec

trait DelayedPlaySpec extends PlaySpec {
  
  def delay(time: Int = 200) = Thread.sleep(time) // If not called, the tests sometimes overlap
}