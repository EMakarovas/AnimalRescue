package com.emakarovas.animalrescue.model.property

import com.emakarovas.animalrescue.model.SearchModel
import com.emakarovas.animalrescue.model.constants.SearchAnimalConstants
import com.emakarovas.animalrescue.model.constants.SearchConstants
import com.emakarovas.animalrescue.model.enumeration.Color
import com.emakarovas.animalrescue.model.enumeration.Size

sealed abstract class SearchInsertableNestedCollectionProperty[T](
    override val entityId: String,
    override val collectionName: String,
    override val nestedCollectionName: String,
    override val value: T)
    extends InsertableNestedCollectionProperty[SearchModel, T] with SearchProperty
    
object SearchInsertableNestedCollectionProperty {
  
  case class SearchAnimalColor(override val entityId: String, override val value: Color)
    extends SearchInsertableNestedCollectionProperty[Color](
        entityId, SearchConstants.SearchAnimalList, SearchAnimalConstants.ColorSet, value)
        
  case class SearchAnimalSize(override val entityId: String, override val value: Size)
    extends SearchInsertableNestedCollectionProperty[Size](
        entityId, SearchConstants.SearchAnimalList, SearchAnimalConstants.SizeSet, value)
      
  case class SearchAnimalTag(override val entityId: String, override val value: String)
    extends SearchInsertableNestedCollectionProperty[String](
        entityId, SearchConstants.SearchAnimalList, SearchAnimalConstants.TagSet, value)
  
}
