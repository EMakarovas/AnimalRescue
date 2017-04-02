package com.emakarovas.animalrescue.persistence.writer

import com.emakarovas.animalrescue.model.OfferAnimalModel
import com.emakarovas.animalrescue.model.OfferTerminationReasonModel
import com.emakarovas.animalrescue.model.constants.OfferAnimalConstants
import com.emakarovas.animalrescue.model.constants.OfferTerminationReasonConstants
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants
import com.emakarovas.animalrescue.persistence.writer.enumeration.ColorWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.GenderWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.OfferTerminationReasonWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.SizeWriter

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class OfferAnimalWriter @Inject() (
    implicit private val imageWriter: ImageWriter,
    implicit private val videoWriter: VideoWriter,
    implicit private val animalTypeDetailsWriter: AnimalTypeDetailsWriter,
    implicit private val genderWriter: GenderWriter,
    implicit val offerTerminationReasonEnumWriter: OfferTerminationReasonWriter,
    implicit val colorWriter: ColorWriter,
    implicit val sizeWriter: SizeWriter)
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
        OfferAnimalConstants.AnimalTypeDetails -> animalTypeDetailsWriter.write(offerAnimal.animalTypeDetails),
        OfferAnimalConstants.Gender -> offerAnimal.gender,
        OfferAnimalConstants.Name -> offerAnimal.name,
        OfferAnimalConstants.Age -> offerAnimal.age,
        OfferAnimalConstants.Description -> offerAnimal.description,
        OfferAnimalConstants.ColorSet -> offerAnimal.colorSet,
        OfferAnimalConstants.Size -> offerAnimal.size,
        OfferAnimalConstants.TagSet -> offerAnimal.tagSet,
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