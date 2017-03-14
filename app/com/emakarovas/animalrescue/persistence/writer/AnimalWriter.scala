package com.emakarovas.animalrescue.persistence.writer

import com.emakarovas.animalrescue.model.AdoptionDetailsModel
import com.emakarovas.animalrescue.model.AnimalModel
import com.emakarovas.animalrescue.model.OfferDetailsModel
import com.emakarovas.animalrescue.model.OfferTerminationReasonModel
import com.emakarovas.animalrescue.model.constants.AdoptionDetailsConstants
import com.emakarovas.animalrescue.model.constants.AnimalConstants
import com.emakarovas.animalrescue.model.constants.OfferDetailsConstants
import com.emakarovas.animalrescue.model.constants.OfferTerminationReasonConstants
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants
import com.emakarovas.animalrescue.persistence.writer.enumeration.AnimalTypeWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.GenderWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.OfferTerminationReasonWriter

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class AnimalWriter @Inject() (
    implicit val animalTypeWriter: AnimalTypeWriter,
    implicit val genderWriter: GenderWriter,
    implicit val imageWriter: ImageWriter,
    implicit val videoWriter: VideoWriter,
    implicit val offerTerminationReasonEnumWriter: OfferTerminationReasonWriter) extends AbstractEntityWriter[AnimalModel] {
  
  implicit object adoptionDetailsWriter extends AbstractEntityWriter[AdoptionDetailsModel] {
    override def write(adoptionDetails: AdoptionDetailsModel): BSONDocument = {
      BSONDocument(
          AdoptionDetailsConstants.OwnerId -> adoptionDetails.ownerId,
          AdoptionDetailsConstants.Date -> adoptionDetails.date)
    }
  }
  
  implicit object offerTerminationReasonWriter extends AbstractEntityWriter[OfferTerminationReasonModel] {
    override def write(offerTerminationReason: OfferTerminationReasonModel): BSONDocument = {
      BSONDocument(
          OfferTerminationReasonConstants.OfferTerminationReason -> offerTerminationReason.offerTerminationReason,
          OfferTerminationReasonConstants.Text -> offerTerminationReason.text)
    }
  }
  
  implicit object offerDetailsWriter extends AbstractEntityWriter[OfferDetailsModel] {
    override def write(offerDetails: OfferDetailsModel): BSONDocument = {
      BSONDocument(
          OfferDetailsConstants.CastrationCost -> offerDetails.castrationCost,
          OfferDetailsConstants.FoodCost -> offerDetails.foodCost,
          OfferDetailsConstants.ShelterCost -> offerDetails.shelterCost,
          OfferDetailsConstants.VaccinationCost -> offerDetails.vaccinationCost,
          OfferDetailsConstants.OfferId -> offerDetails.offerId,
          OfferDetailsConstants.OfferTerminationReason -> offerDetails.offerTerminationReason)
    }
  }

  override def write(animal: AnimalModel): BSONDocument = {
    BSONDocument(
        MongoConstants.MongoId -> animal.id,
        MongoConstants.Data -> BSONDocument(
            AnimalConstants.AnimalType -> animal.animalType,
            AnimalConstants.SpecificType -> animal.specificType,
            AnimalConstants.Gender -> animal.gender,
            AnimalConstants.Name -> animal.name,
            AnimalConstants.Age -> animal.age,
            AnimalConstants.Description -> animal.description,
            AnimalConstants.IsCastrated -> animal.isCastrated,
            AnimalConstants.Image -> animal.image,
            AnimalConstants.Video -> animal.video,
            AnimalConstants.AdoptionDetails -> animal.adoptionDetails,
            AnimalConstants.OfferDetails -> animal.offerDetails))
  } 
  
}