package com.emakarovas.animalrescue.util.impl

import javax.inject._
import com.emakarovas.animalrescue.util.IdGenerator
import com.emakarovas.animalrescue.util.SaltGenerator

@Singleton
class DefaultSaltGenerator @Inject() extends SaltGenerator {
  def generate() = scala.util.Random.alphanumeric.take(20).mkString
}