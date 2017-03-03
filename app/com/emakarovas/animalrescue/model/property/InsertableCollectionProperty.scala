package com.emakarovas.animalrescue.model.property

import com.emakarovas.animalrescue.model.enumeration.ModelEnumeration
import com.emakarovas.animalrescue.model.AbstractModel
import com.emakarovas.animalrescue.model.AbstractPersistableEntity
import com.emakarovas.animalrescue.model.OfferModel

/**
 * Used in the update mechanism; sub-classes of this trait contain all possible
 * collections that can take in new members
 * @tparam T The AbstractModel holding this property
 * @tparam V The type of the members inside the collection
 */
trait InsertableCollectionProperty[T <: AbstractModel with AbstractPersistableEntity, V] 
  extends ModelEnumeration[InsertableCollectionProperty[AbstractModel with AbstractPersistableEntity, V]] {
  /**
   * The name of the collection inside the parent entity 
   */
  def collectionName: String
  /**
   * The type of the members inside the given collection
   */
  def value: V
}