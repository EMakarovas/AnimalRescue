package com.emakarovas.animalrescue.util.generator.impl

import javax.inject._
import com.emakarovas.animalrescue.util.generator.SaltGenerator

@Singleton
class DefaultSaltGenerator @Inject() extends SaltGenerator {
  override def generate() = scala.util.Random.alphanumeric.take(20).mkString
}