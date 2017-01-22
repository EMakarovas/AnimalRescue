package com.emakarovas.animalrescue.util

trait Generator[T] {
  def generate(): T
}