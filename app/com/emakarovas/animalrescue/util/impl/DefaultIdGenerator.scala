package com.emakarovas.animalrescue.util.impl

import javax.inject._
import com.emakarovas.animalrescue.util.IdGenerator

@Singleton
class DefaultIdGenerator @Inject() extends IdGenerator {
  override def generate() = java.util.UUID.randomUUID.toString
}