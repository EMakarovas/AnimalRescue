package com.emakarovas.animalrescue.model.property

import com.emakarovas.animalrescue.model.CommentModel
import com.emakarovas.animalrescue.model.OfferModel
import com.emakarovas.animalrescue.model.constants.OfferConstants

sealed abstract class OfferInsertableCollectionProperty[T](
    override val collectionName: String, override val value: T) extends InsertableCollectionProperty[OfferModel, T] {
  case class OfferCommentInsertableCollectionProperty(override val value: CommentModel)
    extends OfferInsertableCollectionProperty[CommentModel](OfferConstants.CommentList, value)
}