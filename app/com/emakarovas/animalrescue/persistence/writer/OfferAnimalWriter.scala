package com.emakarovas.animalrescue.persistence.writer

import com.emakarovas.animalrescue.model.OfferAnimalModel
import com.emakarovas.animalrescue.model.OfferTerminationReasonModel
import com.emakarovas.animalrescue.model.constants.OfferAnimalConstants
import com.emakarovas.animalrescue.model.constants.OfferTerminationReasonConstants
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants
import com.emakarovas.animalrescue.persistence.writer.enumeration.AnimalTypeWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.GenderWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.OfferTerminationReasonWriter

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class OfferAnimalWriter @Inject() (
    implicit private val imageWriter: ImageWriter,
    implicit private val videoWriter: VideoWriter,
    implicit private val animalTypeWriter: AnimalTypeWriter,
    implicit private val genderWriter: GenderWriter,
    implicit val offerTerminationReasonEnumWriter: OfferTerminationReasonWriter)
      extends AbstractEntityWriter[OfferAnimalModel] {
      
  implicit object offerTerminationReasonWriter extends AbstractEntityWriter[OfferTerminationReasonModel] {
    override def write(offerTerminationReason: OfferTerminationReasonModel): BSONDocument = {
      BSONDocument(
          OfferTerminationReasonConstants.OfferTerminationReason -> offerTerminationReason.offerTerminationReason,
          OfferTerminationReasonConstants.Text -> offerTerminationReason.text)
    }
  }
      
  override def write(offerAnimal: OfferAnimalModel): BSONDocument = {
    BSONDocument(
        MongoConstants.MongoId -> offerAnimal.id,
        OfferAnimalConstants.AnimalType -> offerAnimal.animalType,
        OfferAnimalConstants.SpecificType -> offerAnimal.specificType,
        OfferAnimalConstants.Gender -> offerAnimal.gender,
        OfferAnimalConstants.Name -> offerAnimal.name,
        OfferAnimalConstants.Age -> offerAnimal.age,
        OfferAnimalConstants.Description -> offerAnimal.description,
        OfferAnimalConstants.IsCastrated -> offerAnimal.isCastrated,
        OfferAnimalConstants.Image -> offerAnimal.image,
        OfferAnimalConstants.Video -> offerAnimal.video,
        OfferAnimalConstants.CastrationCost -> offerAnimal.castrationCost,
        OfferAnimalConstants.FoodCost -> offerAnimal.foodCost,
        OfferAnimalConstants.ShelterCost -> offerAnimal.shelterCost,
        OfferAnimalConstants.VaccinationCost -> offerAnimal.vaccinationCost,
        OfferAnimalConstants.OfferTerminationReason -> offerAnimal.offerTerminationReason)
  }
}