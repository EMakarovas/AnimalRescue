package com.emakarovas.animalrescue.model.property

import com.emakarovas.animalrescue.model.OfferAnimalModel
import com.emakarovas.animalrescue.model.OfferModel
import com.emakarovas.animalrescue.model.constants.OfferConstants

sealed abstract class OfferDeletableCollectionProperty[T](
    override val entityId: String, override val collectionName: String) 
    extends DeletableCollectionProperty[OfferModel, T] with OfferProperty
    
object OfferDeletableCollectionProperty {
  case class OfferAnimal(
      override val entityId: String)
      extends OfferDeletableCollectionProperty[OfferAnimalModel](entityId, OfferConstants.OfferAnimalList)
}