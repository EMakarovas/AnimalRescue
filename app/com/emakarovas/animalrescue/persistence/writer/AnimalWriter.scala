package com.emakarovas.animalrescue.persistence.writer

import com.emakarovas.animalrescue.model.AdoptionDetails
import com.emakarovas.animalrescue.model.AnimalModel
import com.emakarovas.animalrescue.model.constants.AdoptionDetailsConstants
import com.emakarovas.animalrescue.model.constants.AnimalConstants
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants
import com.emakarovas.animalrescue.persistence.writer.enumeration.ColorWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.GenderWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.SizeWriter

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class AnimalWriter @Inject() (
    implicit val animalTypeDetailsWriter: AnimalTypeDetailsWriter,
    implicit val genderWriter: GenderWriter,
    implicit val imageWriter: ImageWriter,
    implicit val videoWriter: VideoWriter,
    implicit val colorWriter: ColorWriter,
    implicit val sizeWriter: SizeWriter) extends AbstractEntityWriter[AnimalModel] {
  
  implicit object adoptionDetailsWriter extends AbstractEntityWriter[AdoptionDetails] {
    override def write(adoptionDetails: AdoptionDetails): BSONDocument = {
      BSONDocument(
          AdoptionDetailsConstants.OwnerId -> adoptionDetails.ownerId,
          AdoptionDetailsConstants.Date -> adoptionDetails.date)
    }
  }

  override def write(animal: AnimalModel): BSONDocument = {
    BSONDocument(
        MongoConstants.MongoId -> animal.id,
        AnimalConstants.Url -> animal.url,
        AnimalConstants.AnimalTypeDetails -> animalTypeDetailsWriter.write(animal.animalTypeDetails),
        AnimalConstants.Gender -> animal.gender,
        AnimalConstants.Name -> animal.name,
        AnimalConstants.Age -> animal.age,
        AnimalConstants.Description -> animal.description,
        AnimalConstants.ColorSet -> animal.colorSet,
        AnimalConstants.Size -> animal.size,
        AnimalConstants.TagSet -> animal.tagSet,
        AnimalConstants.IsCastrated -> animal.isCastrated,
        AnimalConstants.Image -> animal.image,
        AnimalConstants.Video -> animal.video,
        AnimalConstants.AdoptionDetails -> animal.adoptionDetails)
  } 
  
}