package com.emakarovas.animalrescue.model.property

import com.emakarovas.animalrescue.model.SearchModel
import com.emakarovas.animalrescue.model.constants.SearchAnimalConstants
import com.emakarovas.animalrescue.model.constants.SearchConstants
import com.emakarovas.animalrescue.model.enumeration.Color
import com.emakarovas.animalrescue.model.enumeration.Size

sealed abstract class SearchDeletableNestedCollectionProperty[T](
    override val entityId: String,
    override val collectionName: String,
    override val nestedCollectionName: String,
    override val nestedCollectionIdField: Option[String],
    override val nestedEntityIdentifier: T)
    extends DeletableNestedCollectionProperty[SearchModel, T] with SearchProperty
    
object SearchDeletableNestedCollectionProperty {
  
  case class SearchAnimalColor(override val entityId: String, override val nestedEntityIdentifier: Color)
    extends SearchDeletableNestedCollectionProperty[Color](
        entityId, SearchConstants.SearchAnimalList, SearchAnimalConstants.ColorSet, None, nestedEntityIdentifier)
        
  case class SearchAnimalSize(override val entityId: String, override val nestedEntityIdentifier: Size)
    extends SearchDeletableNestedCollectionProperty[Size](
        entityId, SearchConstants.SearchAnimalList, SearchAnimalConstants.SizeSet, None, nestedEntityIdentifier)
      
  case class SearchAnimalTag(override val entityId: String, override val nestedEntityIdentifier: String)
    extends SearchDeletableNestedCollectionProperty[String](
        entityId, SearchConstants.SearchAnimalList, SearchAnimalConstants.TagSet, None, nestedEntityIdentifier)
  
}