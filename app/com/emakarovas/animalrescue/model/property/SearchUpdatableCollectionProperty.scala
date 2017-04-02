package com.emakarovas.animalrescue.model.property

import com.emakarovas.animalrescue.model.SearchModel
import com.emakarovas.animalrescue.model.constants.SearchAnimalConstants
import com.emakarovas.animalrescue.model.constants.SearchConstants
import com.emakarovas.animalrescue.model.enumeration.Gender

sealed abstract class SearchUpdatableCollectionProperty[T](
    override val entityId: String, override val collectionName: String, override val propertyName: String, override val value: T) 
    extends UpdatableCollectionProperty[SearchModel, T] with SearchProperty
    
object SearchUpdatableCollectionProperty {
  
  case class SearchAnimalGender(
      override val entityId: String, override val value: Option[Gender])
      extends SearchUpdatableCollectionProperty[Option[Gender]](
          entityId, SearchConstants.SearchAnimalList, SearchAnimalConstants.Gender, value)
  
  case class SearchAnimalCastratedOnly(
      override val entityId: String, override val value: Boolean)
      extends SearchUpdatableCollectionProperty[Boolean](
          entityId, SearchConstants.SearchAnimalList, SearchAnimalConstants.CastratedOnly, value)
      
  case class SearchAnimalMinAge(
      override val entityId: String, override val value: Option[Int])
      extends SearchUpdatableCollectionProperty[Option[Int]](
          entityId, SearchConstants.SearchAnimalList, SearchAnimalConstants.MinAge, value)
  
  case class SearchAnimalMaxAge(
      override val entityId: String, override val value: Option[Int])
      extends SearchUpdatableCollectionProperty[Option[Int]](
          entityId, SearchConstants.SearchAnimalList, SearchAnimalConstants.MaxAge, value)

}