package com.emakarovas.animalrescue.model.property

import com.emakarovas.animalrescue.model.OfferModel
import com.emakarovas.animalrescue.model.constants.OfferAnimalConstants
import com.emakarovas.animalrescue.model.constants.OfferConstants
import com.emakarovas.animalrescue.model.enumeration.Color

sealed abstract class OfferInsertableNestedCollectionProperty[T](
    override val entityId: String,
    override val collectionName: String,
    override val nestedCollectionName: String,
    override val value: T)
    extends InsertableNestedCollectionProperty[OfferModel, T] with OfferProperty
    
object OfferInsertableNestedCollectionProperty {
  
  case class OfferAnimalColor(override val entityId: String, override val value: Color)
    extends OfferInsertableNestedCollectionProperty[Color](
        entityId, OfferConstants.OfferAnimalList, OfferAnimalConstants.ColorSet, value)
      
  case class OfferAnimalTag(override val entityId: String, override val value: String)
    extends OfferInsertableNestedCollectionProperty[String](
        entityId, OfferConstants.OfferAnimalList, OfferAnimalConstants.TagSet, value)
  
}
