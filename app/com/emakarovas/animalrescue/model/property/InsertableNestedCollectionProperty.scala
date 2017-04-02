package com.emakarovas.animalrescue.model.property

import com.emakarovas.animalrescue.model.enumeration.Enum
import com.emakarovas.animalrescue.model.AbstractModel
import com.emakarovas.animalrescue.model.AbstractPersistableEntity
import com.emakarovas.animalrescue.model.OfferModel

/**
 * Used in the update mechanism; sub-classes of this trait contain all possible
 * nested collections that can take in new members
 * @tparam T The AbstractModel holding this property
 * @tparam V The type of the members inside the nested collection
 */
abstract class InsertableNestedCollectionProperty[T <: AbstractModel with AbstractPersistableEntity, +V] extends Property[T] {
  /**
   * The ID of the nested entity
   */
  def entityId: String
  /**
   * The name of the collection inside the parent entity 
   */
  def collectionName: String
  /**
   * The name of the nested collection
   */
  def nestedCollectionName: String
  /**
   * The value to insert into the collection
   */
  def value: V
}