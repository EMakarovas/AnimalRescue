package com.emakarovas.animalrescue.model.property

import com.emakarovas.animalrescue.model.enumeration.Enum
import com.emakarovas.animalrescue.model.AbstractModel
import com.emakarovas.animalrescue.model.AbstractPersistableEntity

abstract class Property[T <: AbstractModel with AbstractPersistableEntity] extends Enum[Property[T]]