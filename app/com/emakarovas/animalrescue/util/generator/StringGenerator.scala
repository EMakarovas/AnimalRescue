package com.emakarovas.animalrescue.util.generator

import com.google.inject.ImplementedBy
import com.emakarovas.animalrescue.util.generator.impl.DefaultStringGenerator

@ImplementedBy(classOf[DefaultStringGenerator])
trait StringGenerator extends Generator[String] {
  def generate(length: Int = 20): String
}