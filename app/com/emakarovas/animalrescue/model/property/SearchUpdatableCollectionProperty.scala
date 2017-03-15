package com.emakarovas.animalrescue.model.property

import com.emakarovas.animalrescue.model.SearchModel
import com.emakarovas.animalrescue.model.constants.SearchAnimalConstants
import com.emakarovas.animalrescue.model.constants.SearchConstants
import com.emakarovas.animalrescue.model.enumeration.Gender

sealed abstract class SearchUpdatableCollectionProperty[T](
    override val entityId: String, override val collectionName: String, override val propertyName: String, override val value: T) 
    extends UpdatableCollectionProperty[SearchModel, T] with SearchProperty
    
object SearchUpdatableCollectionProperty {
  case class SearchAnimalListSpecificType(
      override val entityId: String, override val value: Option[String])
      extends SearchUpdatableCollectionProperty[Option[String]](entityId, SearchConstants.SearchAnimalList, SearchAnimalConstants.SpecificType, value)
  case class SearchAnimalListGender(
      override val entityId: String, override val value: Gender)
      extends SearchUpdatableCollectionProperty[Gender](entityId, SearchConstants.SearchAnimalList, SearchAnimalConstants.Gender, value)
  case class SearchAnimalListMinAge(
      override val entityId: String, override val value: Option[Int])
      extends SearchUpdatableCollectionProperty[Option[Int]](entityId, SearchConstants.SearchAnimalList, SearchAnimalConstants.MinAge, value)
  case class SearchAnimalListMaxAge(
      override val entityId: String, override val value: Option[Int])
      extends SearchUpdatableCollectionProperty[Option[Int]](entityId, SearchConstants.SearchAnimalList, SearchAnimalConstants.MaxAge, value)
}