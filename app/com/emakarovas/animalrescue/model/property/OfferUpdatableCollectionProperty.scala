package com.emakarovas.animalrescue.model.property

import com.emakarovas.animalrescue.model.ImageModel
import com.emakarovas.animalrescue.model.OfferModel
import com.emakarovas.animalrescue.model.VideoModel
import com.emakarovas.animalrescue.model.constants.OfferAnimalConstants
import com.emakarovas.animalrescue.model.constants.OfferConstants
import com.emakarovas.animalrescue.model.enumeration.Gender
import com.emakarovas.animalrescue.model.enumeration.Size

sealed abstract class OfferUpdatableCollectionProperty[T](
    override val entityId: String, override val collectionName: String, override val propertyName: String, override val value: T) 
    extends UpdatableCollectionProperty[OfferModel, T] with OfferProperty
    
object OfferUpdatableCollectionProperty {
  
  case class OfferAnimalGender(
      override val entityId: String, override val value: Option[Gender])
      extends OfferUpdatableCollectionProperty[Option[Gender]](entityId, OfferConstants.OfferAnimalList, OfferAnimalConstants.Gender, value)
  
  case class OfferAnimalName(
      override val entityId: String, override val value: Option[String])
      extends OfferUpdatableCollectionProperty[Option[String]](entityId, OfferConstants.OfferAnimalList, OfferAnimalConstants.Name, value)
  
  case class OfferAnimalAge(
      override val entityId: String, override val value: Int)
      extends OfferUpdatableCollectionProperty[Int](entityId, OfferConstants.OfferAnimalList, OfferAnimalConstants.Age, value)
  
  case class OfferAnimalDescription(
      override val entityId: String, override val value: Option[String])
      extends OfferUpdatableCollectionProperty[Option[String]](entityId, OfferConstants.OfferAnimalList, OfferAnimalConstants.Description, value)
      
  case class OfferAnimalSize(
      override val entityId: String, override val value: Size)
      extends OfferUpdatableCollectionProperty[Size](entityId, OfferConstants.OfferAnimalList, OfferAnimalConstants.Size, value)
      
  case class OfferAnimalIsCastrated(
      override val entityId: String, override val value: Boolean)
      extends OfferUpdatableCollectionProperty[Boolean](entityId, OfferConstants.OfferAnimalList, OfferAnimalConstants.IsCastrated, value)
  
  case class OfferAnimalImage(
      override val entityId: String, override val value: Option[ImageModel])
      extends OfferUpdatableCollectionProperty[Option[ImageModel]](entityId, OfferConstants.OfferAnimalList, OfferAnimalConstants.Image, value)
  
  case class OfferAnimalVideo(
      override val entityId: String, override val value: Option[VideoModel])
      extends OfferUpdatableCollectionProperty[Option[VideoModel]](entityId, OfferConstants.OfferAnimalList, OfferAnimalConstants.Video, value)
  
  case class OfferAnimalCastrationCost(
      override val entityId: String, override val value: Option[Double])
      extends OfferUpdatableCollectionProperty[Option[Double]](entityId, OfferConstants.OfferAnimalList, OfferAnimalConstants.CastrationCost, value)
  
  case class OfferAnimalFoodCost(
      override val entityId: String, override val value: Option[Double])
      extends OfferUpdatableCollectionProperty[Option[Double]](entityId, OfferConstants.OfferAnimalList, OfferAnimalConstants.FoodCost, value)
  
  case class OfferAnimalShelterCost(
      override val entityId: String, override val value: Option[Double])
      extends OfferUpdatableCollectionProperty[Option[Double]](entityId, OfferConstants.OfferAnimalList, OfferAnimalConstants.ShelterCost, value)
  
  case class OfferAnimalVaccinationCost(
      override val entityId: String, override val value: Option[Double])
      extends OfferUpdatableCollectionProperty[Option[Double]](entityId, OfferConstants.OfferAnimalList, OfferAnimalConstants.VaccinationCost, value)

}