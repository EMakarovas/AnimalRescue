package com.emakarovas.animalrescue.model.property

import com.emakarovas.animalrescue.model.OfferModel
import com.emakarovas.animalrescue.model.constants.OfferAnimalConstants
import com.emakarovas.animalrescue.model.constants.OfferConstants
import com.emakarovas.animalrescue.model.enumeration.Color

sealed abstract class OfferDeletableNestedCollectionProperty[T](
    override val entityId: String,
    override val collectionName: String,
    override val nestedCollectionName: String,
    override val nestedCollectionIdField: Option[String],
    override val nestedEntityIdentifier: T)
    extends DeletableNestedCollectionProperty[OfferModel, T] with OfferProperty
    
object OfferDeletableNestedCollectionProperty {
  
  case class OfferAnimalColor(override val entityId: String, override val nestedEntityIdentifier: Color)
    extends OfferDeletableNestedCollectionProperty[Color](
        entityId, OfferConstants.OfferAnimalList, OfferAnimalConstants.ColorSet, None, nestedEntityIdentifier)
      
  case class OfferAnimalTag(override val entityId: String, override val nestedEntityIdentifier: String)
    extends OfferDeletableNestedCollectionProperty[String](
        entityId, OfferConstants.OfferAnimalList, OfferAnimalConstants.TagSet, None, nestedEntityIdentifier)
  
}