package com.emakarovas.animalrescue.util.impl

import com.emakarovas.animalrescue.util.SaltGenerator

import javax.inject._

@Singleton
class DefaultSaltGenerator @Inject() extends SaltGenerator {
  override def generate() = scala.util.Random.alphanumeric.take(20).mkString
}