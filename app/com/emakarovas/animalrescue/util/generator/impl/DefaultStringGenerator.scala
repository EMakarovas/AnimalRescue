package com.emakarovas.animalrescue.util.generator.impl

import javax.inject.Singleton
import javax.inject.Inject
import com.emakarovas.animalrescue.util.generator.StringGenerator

@Singleton
class DefaultStringGenerator @Inject() extends StringGenerator {
  override def generate(length: Int = 20) = scala.util.Random.alphanumeric.take(length).mkString
}