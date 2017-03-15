package com.emakarovas.animalrescue.persistence.writer

import com.emakarovas.animalrescue.model.AdoptionDetailsModel
import com.emakarovas.animalrescue.model.AnimalModel
import com.emakarovas.animalrescue.model.constants.AdoptionDetailsConstants
import com.emakarovas.animalrescue.model.constants.AnimalConstants
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants
import com.emakarovas.animalrescue.persistence.writer.enumeration.AnimalTypeWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.GenderWriter

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class AnimalWriter @Inject() (
    implicit val animalTypeWriter: AnimalTypeWriter,
    implicit val genderWriter: GenderWriter,
    implicit val imageWriter: ImageWriter,
    implicit val videoWriter: VideoWriter) extends AbstractEntityWriter[AnimalModel] {
  
  implicit object adoptionDetailsWriter extends AbstractEntityWriter[AdoptionDetailsModel] {
    override def write(adoptionDetails: AdoptionDetailsModel): BSONDocument = {
      BSONDocument(
          AdoptionDetailsConstants.OwnerId -> adoptionDetails.ownerId,
          AdoptionDetailsConstants.Date -> adoptionDetails.date)
    }
  }

  override def write(animal: AnimalModel): BSONDocument = {
    BSONDocument(
        MongoConstants.MongoId -> animal.id,
        MongoConstants.Data -> BSONDocument(
            AnimalConstants.Url -> animal.url,
            AnimalConstants.AnimalType -> animal.animalType,
            AnimalConstants.SpecificType -> animal.specificType,
            AnimalConstants.Gender -> animal.gender,
            AnimalConstants.Name -> animal.name,
            AnimalConstants.Age -> animal.age,
            AnimalConstants.Description -> animal.description,
            AnimalConstants.IsCastrated -> animal.isCastrated,
            AnimalConstants.Image -> animal.image,
            AnimalConstants.Video -> animal.video,
            AnimalConstants.AdoptionDetails -> animal.adoptionDetails))
  } 
  
}