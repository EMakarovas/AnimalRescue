package com.emakarovas.animalrescue.model.enumeration

trait Enum[A] {
  // TODO - this doesn't work
  trait Value { self: A =>
    _values :+= this
  }
  private var _values = List.empty[A]
  def values = _values
}