package com.emakarovas.animalrescue.util

trait IdGenerator extends Generator[String] {
  override def generate(): String
}