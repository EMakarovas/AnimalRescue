package com.emakarovas.animalrescue.model.property

import com.emakarovas.animalrescue.model.enumeration.Enum
import com.emakarovas.animalrescue.model.AbstractModel
import com.emakarovas.animalrescue.model.AbstractPersistableEntity
import com.emakarovas.animalrescue.model.OfferModel

/**
 * Used in the update mechanism; sub-classes of this trait contain all possible
 * nested collections that can have their members deleted
 * @tparam T The AbstractModel holding this property
 * @tparam V The type of the members inside the collection
 */
abstract class DeletableNestedCollectionProperty[T <: AbstractModel with AbstractPersistableEntity, +V] extends Property[T] {
  /**
   *  The ID of the entity stored in a collection
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
   * The field used to identify the entity inside the collection.
   * Can be None if the value itself is an identifier (e.g. array of enums)
   */
  def nestedCollectionIdField: Option[String]
  /**
   *  The identification field value
   */
  def nestedEntityIdentifier: V
}

