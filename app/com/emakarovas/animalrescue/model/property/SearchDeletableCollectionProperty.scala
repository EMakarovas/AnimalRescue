package com.emakarovas.animalrescue.model.property

import com.emakarovas.animalrescue.model.SearchModel

sealed abstract class SearchDeletableCollectionProperty[T](
    override val entityId: String, override val collectionName: String) 
    extends DeletableCollectionProperty[SearchModel, T] with SearchProperty
    
object SearchDeletableCollectionProperty {
  case class SearchSearchAnimalDeletableCollectionProperty[SearchAnimalModel](
      override val entityId: String, override val collectionName: String)
      extends SearchDeletableCollectionProperty[SearchAnimalModel](entityId, collectionName)
}