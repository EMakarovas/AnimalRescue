package com.emakarovas.animalrescue.model.property

import com.emakarovas.animalrescue.model.CommentModel
import com.emakarovas.animalrescue.model.SearchAnimalModel
import com.emakarovas.animalrescue.model.SearchModel
import com.emakarovas.animalrescue.model.constants.SearchConstants

sealed abstract class SearchInsertableCollectionProperty[T](
    override val collectionName: String, override val value: T) extends InsertableCollectionProperty[SearchModel, T] {
  case class SearchCommentInsertableCollectionProperty(override val value: CommentModel)
    extends SearchInsertableCollectionProperty[CommentModel](SearchConstants.CommentList, value)
  case class SearchSearchAnimalInsertableCollectionProperty(override val value: SearchAnimalModel)
    extends SearchInsertableCollectionProperty[SearchAnimalModel](SearchConstants.SearchAnimalList, value)
}