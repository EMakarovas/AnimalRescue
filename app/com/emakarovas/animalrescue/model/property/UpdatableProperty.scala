package com.emakarovas.animalrescue.model.property

import com.emakarovas.animalrescue.model.AbstractModel
import com.emakarovas.animalrescue.model.AbstractPersistableEntity

/**
 * Used in the update mechanism; sub-classes of this trait contain
 * all possible updatable fields for a given persistable entity
 * @tparam T The AbstractModel holding this property
 * @tparam V The type of the property
 */
abstract class UpdatableProperty[T <: AbstractModel with AbstractPersistableEntity, +V] extends Property[T] {
  def name: String
  def value: V
}