package com.emakarovas.animalrescue.model.property

import com.emakarovas.animalrescue.model.SearchModel
import com.emakarovas.animalrescue.model.constants.SearchConstants
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants

sealed abstract class SearchDeletableCollectionProperty[T](
    override val idField: Option[String], 
    override val entityIdentifier: T, 
    override val collectionName: String) 
    extends DeletableCollectionProperty[SearchModel, T] with SearchProperty
    
object SearchDeletableCollectionProperty {
  
  case class SearchAnimal(
      override val entityIdentifier: String)
        extends SearchDeletableCollectionProperty[String](
            Some(MongoConstants.MongoId), entityIdentifier, SearchConstants.SearchAnimalList)
      
}
