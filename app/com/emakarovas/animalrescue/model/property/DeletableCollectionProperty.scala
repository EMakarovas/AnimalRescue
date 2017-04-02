package com.emakarovas.animalrescue.model.property

import com.emakarovas.animalrescue.model.enumeration.Enum
import com.emakarovas.animalrescue.model.AbstractModel
import com.emakarovas.animalrescue.model.AbstractPersistableEntity
import com.emakarovas.animalrescue.model.OfferModel

/**
 * Used in the update mechanism; sub-classes of this trait contain all possible
 * collections that can take in new members
 * @tparam T The AbstractModel holding this property
 * @tparam V The type of the members inside the collection
 */
abstract class DeletableCollectionProperty[T <: AbstractModel with AbstractPersistableEntity, +V] extends Property[T] {
  /**
   * The field used to identify the entity inside the collection.
   * Can be None if the value itself is an identifier (e.g. array of enums)
   */
  def idField: Option[String]
  /**
   *  The identification field value
   */
  def entityIdentifier: V
  /**
   * The name of the collection inside the parent entity 
   */
  def collectionName: String
}

