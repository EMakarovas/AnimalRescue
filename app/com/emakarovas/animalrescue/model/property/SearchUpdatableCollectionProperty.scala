package com.emakarovas.animalrescue.model.property

import com.emakarovas.animalrescue.model.SearchModel
import com.emakarovas.animalrescue.model.ImageModel
import com.emakarovas.animalrescue.model.VideoModel
import com.emakarovas.animalrescue.model.enumeration.AnimalType
import com.emakarovas.animalrescue.model.enumeration.Gender
import com.emakarovas.animalrescue.model.constants.SearchConstants
import com.emakarovas.animalrescue.model.constants.SearchAnimalConstants

sealed abstract class SearchUpdatableCollectionProperty[T](
    override val entityId: String, override val collectionName: String, override val propertyName: String, override val value: T) 
    extends UpdatableCollectionProperty[SearchModel, T] {
  case class SearchAnimalListAnimalTypeProperty(
      override val entityId: String, override val value: AnimalType) 
      extends SearchUpdatableCollectionProperty[AnimalType](entityId, SearchConstants.SearchAnimalList, SearchAnimalConstants.AnimalType, value)
  case class SearchAnimalListSpecificTypeProperty(
      override val entityId: String, override val value: Option[String])
      extends SearchUpdatableCollectionProperty[Option[String]](entityId, SearchConstants.SearchAnimalList, SearchAnimalConstants.SpecificType, value)
  case class SearchAnimalListGenderProperty(
      override val entityId: String, override val value: Gender)
      extends SearchUpdatableCollectionProperty[Gender](entityId, SearchConstants.SearchAnimalList, SearchAnimalConstants.Gender, value)
  case class SearchAnimalListMinAgeProperty(
      override val entityId: String, override val value: Option[Int])
      extends SearchUpdatableCollectionProperty[Option[Int]](entityId, SearchConstants.SearchAnimalList, SearchAnimalConstants.MinAge, value)
  case class SearchAnimalListMaxAgeProperty(
      override val entityId: String, override val value: Option[Int])
      extends SearchUpdatableCollectionProperty[Option[Int]](entityId, SearchConstants.SearchAnimalList, SearchAnimalConstants.MaxAge, value)
}