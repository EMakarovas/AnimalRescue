package com.emakarovas.animalrescue.model.constants

import java.util.Date

import com.emakarovas.animalrescue.model.enumeration.AnimalType
import com.emakarovas.animalrescue.model.enumeration.Gender
import com.emakarovas.animalrescue.model.enumeration.OfferTerminationReason

object AnimalConstants {
  val Id = "id"
  val AnimalType = "animalType"
  val SpecificType = "specificType"
  val Gender = "gender"
  val Name = "name"
  val Age = "age"
  val Description = "description"
  val IsCastrated = "isCastrated"
  val Image = "image"
  val Video = "video"
  val AdoptionDetails = "adoptionDetails"
  val OfferDetails = "offerDetails"
}

object OfferDetailsConstants {
  val CastrationCost = "castrationCost"
  val FoodCost = "foodCost"
  val ShelterCost = "shelterCost"
  val VaccinationCost = "vaccinationCost"
  val OfferId = "offerId"
  val OfferTerminationReason = "offerTerminationReason"
}

object OfferTerminationReasonConstants {
  val OfferTerminationReason = "offerTerminationReason"
  val Text = "text"
}

object AdoptionDetailsConstants {
  val OwnerId = "ownerId"
  val Date = "date"
}