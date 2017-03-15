package com.emakarovas.animalrescue.model.property

import com.emakarovas.animalrescue.model.ImageModel
import com.emakarovas.animalrescue.model.OfferModel
import com.emakarovas.animalrescue.model.VideoModel
import com.emakarovas.animalrescue.model.constants.OfferAnimalConstants
import com.emakarovas.animalrescue.model.constants.OfferConstants
import com.emakarovas.animalrescue.model.enumeration.Gender

sealed abstract class OfferUpdatableCollectionProperty[T](
    override val entityId: String, override val collectionName: String, override val propertyName: String, override val value: T) 
    extends UpdatableCollectionProperty[OfferModel, T] with OfferProperty
    
object OfferUpdatableCollectionProperty {
  case class OfferAnimalListSpecificType(
      override val entityId: String, override val value: Option[String])
      extends OfferUpdatableCollectionProperty[Option[String]](entityId, OfferConstants.OfferAnimalList, OfferAnimalConstants.SpecificType, value)
  case class OfferAnimalListGender(
      override val entityId: String, override val value: Gender)
      extends OfferUpdatableCollectionProperty[Gender](entityId, OfferConstants.OfferAnimalList, OfferAnimalConstants.Gender, value)
  case class OfferAnimalListName(
      override val entityId: String, override val value: Option[String])
      extends OfferUpdatableCollectionProperty[Option[String]](entityId, OfferConstants.OfferAnimalList, OfferAnimalConstants.Name, value)
  case class OfferAnimalListAge(
      override val entityId: String, override val value: Option[Int])
      extends OfferUpdatableCollectionProperty[Option[Int]](entityId, OfferConstants.OfferAnimalList, OfferAnimalConstants.Age, value)
  case class OfferAnimalListIsCastrated(
      override val entityId: String, override val value: Boolean)
      extends OfferUpdatableCollectionProperty[Boolean](entityId, OfferConstants.OfferAnimalList, OfferAnimalConstants.IsCastrated, value)
  case class OfferAnimalListImage(
      override val entityId: String, override val value: Option[ImageModel])
      extends OfferUpdatableCollectionProperty[Option[ImageModel]](entityId, OfferConstants.OfferAnimalList, OfferAnimalConstants.Image, value)
  case class OfferAnimalListVideo(
      override val entityId: String, override val value: Option[VideoModel])
      extends OfferUpdatableCollectionProperty[Option[VideoModel]](entityId, OfferConstants.OfferAnimalList, OfferAnimalConstants.Video, value)
  case class OfferAnimalListCastrationCost(
      override val entityId: String, override val value: Option[Double])
      extends OfferUpdatableCollectionProperty[Option[Double]](entityId, OfferConstants.OfferAnimalList, OfferAnimalConstants.CastrationCost, value)
  case class OfferAnimalListFoodCost(
      override val entityId: String, override val value: Option[Double])
      extends OfferUpdatableCollectionProperty[Option[Double]](entityId, OfferConstants.OfferAnimalList, OfferAnimalConstants.FoodCost, value)
  case class OfferAnimalListShelterCost(
      override val entityId: String, override val value: Option[Double])
      extends OfferUpdatableCollectionProperty[Option[Double]](entityId, OfferConstants.OfferAnimalList, OfferAnimalConstants.ShelterCost, value)
  case class OfferAnimalListVaccinationCost(
      override val entityId: String, override val value: Option[Double])
      extends OfferUpdatableCollectionProperty[Option[Double]](entityId, OfferConstants.OfferAnimalList, OfferAnimalConstants.VaccinationCost, value)
}