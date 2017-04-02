package com.emakarovas.animalrescue.persistence.writer.enumeration

import com.emakarovas.animalrescue.model.enumeration.Color

import javax.inject.Singleton
import reactivemongo.bson.BSONString

@Singleton
class ColorWriter extends EnumerationWriter[Color] {
  override def write(gender: Color) = BSONString(gender.toString)
}