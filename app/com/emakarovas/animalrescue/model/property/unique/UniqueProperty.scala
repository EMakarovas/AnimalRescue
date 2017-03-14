package com.emakarovas.animalrescue.model.property.unique

import com.emakarovas.animalrescue.model.AbstractModel
import com.emakarovas.animalrescue.model.enumeration.Enum

abstract class UniqueProperty[T <: AbstractModel, +V <: Any](
   val propertyName: String, val propertyValue: V) extends Enum[UniqueProperty[T, _]]