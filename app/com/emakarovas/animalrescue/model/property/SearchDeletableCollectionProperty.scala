package com.emakarovas.animalrescue.model.property

import com.emakarovas.animalrescue.model.SearchAnimalModel
import com.emakarovas.animalrescue.model.SearchModel
import com.emakarovas.animalrescue.model.constants.SearchConstants

sealed abstract class SearchDeletableCollectionProperty[T](
    override val entityId: String, override val collectionName: String) 
    extends DeletableCollectionProperty[SearchModel, T] with SearchProperty
    
object SearchDeletableCollectionProperty {
  case class SearchAnimal(
      override val entityId: String)
      extends SearchDeletableCollectionProperty[SearchAnimalModel](entityId, SearchConstants.SearchAnimalList)
}