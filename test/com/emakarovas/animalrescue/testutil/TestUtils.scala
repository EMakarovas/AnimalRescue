package com.emakarovas.animalrescue.testutil

import java.util.Calendar
import java.util.Date

object TestUtils {
  def buildDate(day: Int, month: Int, year: Int): Date = {
    val cal = Calendar.getInstance
    cal.set(Calendar.DAY_OF_MONTH, day);
    cal.set(Calendar.MONTH, month);
    cal.set(Calendar.YEAR, year);
    cal.getTime
  }
}