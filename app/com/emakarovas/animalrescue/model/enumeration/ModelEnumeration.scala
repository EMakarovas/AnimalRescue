package com.emakarovas.animalrescue.model.enumeration

trait ModelEnumeration[A] {
  trait Value { self: A =>
    _values :+= this
  }
  private var _values = List.empty[A]
  def values = _values
}