package com.emakarovas.animalrescue.model.property

import com.emakarovas.animalrescue.model.CommentModel
import com.emakarovas.animalrescue.model.OfferAnimalModel
import com.emakarovas.animalrescue.model.OfferModel
import com.emakarovas.animalrescue.model.constants.OfferConstants

sealed abstract class OfferInsertableCollectionProperty[T](
    override val collectionName: String, override val value: T) extends InsertableCollectionProperty[OfferModel, T] with OfferProperty

object OfferInsertableCollectionProperty {
  
  case class Comment(override val value: CommentModel)
    extends OfferInsertableCollectionProperty[CommentModel](OfferConstants.CommentList, value)
    
  case class OfferAnimal(override val value: OfferAnimalModel)
    extends OfferInsertableCollectionProperty[OfferAnimalModel](OfferConstants.OfferAnimalList, value)
    
}