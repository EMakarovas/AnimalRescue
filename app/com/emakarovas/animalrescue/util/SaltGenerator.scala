package com.emakarovas.animalrescue.util

trait SaltGenerator extends Generator[String] {
  override def generate(): String
}