package com.emakarovas.animalrescue.model

import java.util.Date

import com.emakarovas.animalrescue.model.enumeration.AnimalType
import com.emakarovas.animalrescue.model.enumeration.Gender
import com.emakarovas.animalrescue.model.enumeration.OfferTerminationReason

case class AnimalModel
  (override val id: String,
   override val animalType: AnimalType,
   override val specificType: Option[String],
   override val gender: Gender,
   name: Option[String],
   age: Option[Int], // stored as months
   description: Option[String],
   image: Option[ImageModel],
   video: Option[VideoModel],
   adoptionDetails: Option[AdoptionDetailsModel],
   offerDetails: Option[OfferDetailsModel])
   extends AbstractModel(id) with AbstractAnimalEntity with AbstractPersistableEntity

case class OfferDetailsModel
  (foodCost: Option[Double],
   shelterCost: Option[Double],
   vaccinationCost: Option[Double],
   offerId: String,
   offerTerminationReason: Option[OfferTerminationReasonModel]) extends AbstractEntity

case class OfferTerminationReasonModel
  (offerTerminationReason: OfferTerminationReason,
   text: Option[String]) extends AbstractEntity

case class AdoptionDetailsModel
  (ownerId: Option[String], // ID of the user
   date: Date) extends AbstractEntity
