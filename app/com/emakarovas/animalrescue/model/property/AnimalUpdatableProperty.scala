package com.emakarovas.animalrescue.model.property

import com.emakarovas.animalrescue.model.AnimalModel
import com.emakarovas.animalrescue.model.ImageModel
import com.emakarovas.animalrescue.model.VideoModel
import com.emakarovas.animalrescue.model.constants.AnimalConstants
import com.emakarovas.animalrescue.model.enumeration.AnimalType
import com.emakarovas.animalrescue.model.enumeration.Gender
import com.emakarovas.animalrescue.model.enumeration.OfferTerminationReason
import com.emakarovas.animalrescue.model.constants.OfferDetailsConstants

sealed abstract class AnimalUpdatableProperty[T](
    override val name: String, override val value: T) extends UpdatableProperty[AnimalModel, T] {
  case class AnimalAnimalTypeProperty(override val value: AnimalType) 
    extends AnimalUpdatableProperty[AnimalType](AnimalConstants.AnimalType, value)
  case class AnimalSpecificTypeProperty(override val value: Option[String]) 
    extends AnimalUpdatableProperty[Option[String]](AnimalConstants.SpecificType, value)
  case class AnimalGenderProperty(override val value: Gender) 
    extends AnimalUpdatableProperty[Gender](AnimalConstants.Gender, value)
  case class AnimalNameProperty(override val value: Option[String]) 
    extends AnimalUpdatableProperty[Option[String]](AnimalConstants.Name, value)
  case class AnimalAgeProperty(override val value: Option[Int]) 
    extends AnimalUpdatableProperty[Option[Int]](AnimalConstants.Age, value)
  case class AnimalDescriptionProperty(override val value: Option[String]) 
    extends AnimalUpdatableProperty[Option[String]](AnimalConstants.Description, value)
  case class AnimalImageProperty(override val value: Option[ImageModel]) 
    extends AnimalUpdatableProperty[Option[ImageModel]](AnimalConstants.Image, value)
  case class AnimalVideoProperty(override val value: Option[VideoModel]) 
    extends AnimalUpdatableProperty[Option[VideoModel]](AnimalConstants.Video, value)
  case class AnimalOfferTerminationReasonProperty(override val value: Option[OfferTerminationReason]) 
    extends AnimalUpdatableProperty(AnimalConstants.OfferDetails + "." + OfferDetailsConstants.OfferTerminationReason, value)
  case class AnimalFoodCostProperty(override val value: Option[Double]) 
    extends AnimalUpdatableProperty[Option[Double]](AnimalConstants.OfferDetails + "." + OfferDetailsConstants.FoodCost, value)
  case class AnimalShelterCostProperty(override val value: Option[Double]) 
    extends AnimalUpdatableProperty[Option[Double]](AnimalConstants.OfferDetails + "." + OfferDetailsConstants.ShelterCost, value)
  case class AnimalVaccinationCostProperty(override val value: Option[Double]) 
    extends AnimalUpdatableProperty[Option[Double]](AnimalConstants.OfferDetails + "." + OfferDetailsConstants.VaccinationCost, value)

}