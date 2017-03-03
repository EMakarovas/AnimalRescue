package com.emakarovas.animalrescue.model.property

import com.emakarovas.animalrescue.model.enumeration.ModelEnumeration
import com.emakarovas.animalrescue.model.AbstractModel
import com.emakarovas.animalrescue.model.AbstractPersistableEntity
import com.emakarovas.animalrescue.model.OfferModel

/**
 * Used in the update mechanism; sub-classes of this trait contain all possible 
 * updatable fields that are stored in a collection for a given persistable entity
 * @tparam T The AbstractModel holding this collection property
 * @tparam V The type of the property
 */
trait UpdatableCollectionProperty[T <: AbstractModel with AbstractPersistableEntity, V] 
  extends ModelEnumeration[UpdatableCollectionProperty[AbstractModel with AbstractPersistableEntity, V]] {
  /**
   *  The ID of the entity stored in a collection
   */
  def entityId: String
  /**
   * The name of the collection inside the parent entity 
   */
  def collectionName: String
  /**
   * The name of the selected entity's property
   */
  def propertyName: String
  /**
   * The value assigned to the selected entity's property
   */
  def value: V
}