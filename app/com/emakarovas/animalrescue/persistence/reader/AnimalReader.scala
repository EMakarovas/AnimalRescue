package com.emakarovas.animalrescue.persistence.reader

import java.util.Date

import com.emakarovas.animalrescue.model.AdoptionDetailsModel
import com.emakarovas.animalrescue.model.AnimalModel
import com.emakarovas.animalrescue.model.ImageModel
import com.emakarovas.animalrescue.model.VideoModel
import com.emakarovas.animalrescue.model.constants.AdoptionDetailsConstants
import com.emakarovas.animalrescue.model.constants.AnimalConstants
import com.emakarovas.animalrescue.model.enumeration.AnimalType
import com.emakarovas.animalrescue.model.enumeration.Gender
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants
import com.emakarovas.animalrescue.persistence.reader.enumeration.AnimalTypeReader
import com.emakarovas.animalrescue.persistence.reader.enumeration.GenderReader

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.bson.BSONDocument
import reactivemongo.bson.BSONDocumentReader

@Singleton
class AnimalReader @Inject() 
  (implicit val animalTypeReader: AnimalTypeReader,
   implicit val genderReader: GenderReader,
   implicit val imageReader: ImageReader,
   implicit val videoReader: VideoReader) extends AbstractEntityReader[AnimalModel] {
  
  implicit object adoptionDetailsReader extends BSONDocumentReader[AdoptionDetailsModel] {
    override def read(doc: BSONDocument): AdoptionDetailsModel = {
      val ownerIdOpt = doc.getAs[String](AdoptionDetailsConstants.OwnerId)
      val date = doc.getAs[Date](AdoptionDetailsConstants.Date).get
      AdoptionDetailsModel(ownerIdOpt, date)
    }
  }

  override def read(topDoc: BSONDocument): AnimalModel = {
    val id = topDoc.getAs[String](MongoConstants.MongoId).get
    val doc = topDoc.getAs[BSONDocument](MongoConstants.Data).get
    val url = doc.getAs[String](AnimalConstants.Url).get
    val animalType = doc.getAs[AnimalType](AnimalConstants.AnimalType).get
    val specificTypeOpt = doc.getAs[String](AnimalConstants.SpecificType)
    val gender = doc.getAs[Gender](AnimalConstants.Gender).get
    val nameOpt = doc.getAs[String](AnimalConstants.Name)
    val ageOpt = doc.getAs[Int](AnimalConstants.Age)
    val descriptionOpt = doc.getAs[String](AnimalConstants.Description)
    val isCastrated = doc.getAs[Boolean](AnimalConstants.IsCastrated).get
    val imageOpt = doc.getAs[ImageModel](AnimalConstants.Image)
    val videoOpt = doc.getAs[VideoModel](AnimalConstants.Video)
    val adoptionDetailsOpt = doc.getAs[AdoptionDetailsModel](AnimalConstants.AdoptionDetails)
    AnimalModel(id, url, animalType, specificTypeOpt, gender, nameOpt, ageOpt, descriptionOpt, isCastrated, imageOpt, videoOpt,
        adoptionDetailsOpt)
  }
  
}