package com.emakarovas.animalrescue.util.generator

import com.google.inject.ImplementedBy
import com.emakarovas.animalrescue.util.generator.impl.DefaultSaltGenerator

@ImplementedBy(classOf[DefaultSaltGenerator])
trait SaltGenerator extends Generator[String] {
  def generate(): String
}