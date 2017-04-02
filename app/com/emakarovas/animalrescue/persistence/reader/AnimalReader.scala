package com.emakarovas.animalrescue.persistence.reader

import java.util.Date

import com.emakarovas.animalrescue.model.AdoptionDetails
import com.emakarovas.animalrescue.model.AnimalModel
import com.emakarovas.animalrescue.model.AnimalTypeDetails
import com.emakarovas.animalrescue.model.ImageModel
import com.emakarovas.animalrescue.model.VideoModel
import com.emakarovas.animalrescue.model.constants.AdoptionDetailsConstants
import com.emakarovas.animalrescue.model.constants.AnimalConstants
import com.emakarovas.animalrescue.model.enumeration.Animal
import com.emakarovas.animalrescue.model.enumeration.Color
import com.emakarovas.animalrescue.model.enumeration.Gender
import com.emakarovas.animalrescue.model.enumeration.Size
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants
import com.emakarovas.animalrescue.persistence.reader.enumeration.ColorReader
import com.emakarovas.animalrescue.persistence.reader.enumeration.GenderReader
import com.emakarovas.animalrescue.persistence.reader.enumeration.SizeReader

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.bson.BSONDocument
import reactivemongo.bson.BSONDocumentReader

@Singleton
class AnimalReader @Inject() 
  (implicit val animalTypeDetailsReader: AnimalTypeDetailsReader,
   implicit val genderReader: GenderReader,
   implicit val imageReader: ImageReader,
   implicit val videoReader: VideoReader,
   implicit val colorReader: ColorReader,
   implicit val sizeReader: SizeReader) extends AbstractEntityReader[AnimalModel] {
  
  implicit object adoptionDetailsReader extends BSONDocumentReader[AdoptionDetails] {
    override def read(doc: BSONDocument): AdoptionDetails = {
      val ownerIdOpt = doc.getAs[String](AdoptionDetailsConstants.OwnerId)
      val date = doc.getAs[Date](AdoptionDetailsConstants.Date).get
      AdoptionDetails(ownerIdOpt, date)
    }
  }

  override def read(doc: BSONDocument): AnimalModel = {
    val id = doc.getAs[String](MongoConstants.MongoId).get
    val url = doc.getAs[String](AnimalConstants.Url).get
    val animalTypeDetails = doc.getAs[AnimalTypeDetails[_ <: Animal]](AnimalConstants.AnimalTypeDetails).get
    val genderOpt = doc.getAs[Gender](AnimalConstants.Gender)
    val nameOpt = doc.getAs[String](AnimalConstants.Name)
    val age = doc.getAs[Int](AnimalConstants.Age).get
    val descriptionOpt = doc.getAs[String](AnimalConstants.Description)
    val colorSet = doc.getAs[Set[Color]](AnimalConstants.ColorSet).get
    val size = doc.getAs[Size](AnimalConstants.Size).get
    val tagSet = doc.getAs[Set[String]](AnimalConstants.TagSet).get
    val isCastrated = doc.getAs[Boolean](AnimalConstants.IsCastrated).get
    val imageOpt = doc.getAs[ImageModel](AnimalConstants.Image)
    val videoOpt = doc.getAs[VideoModel](AnimalConstants.Video)
    val adoptionDetailsOpt = doc.getAs[AdoptionDetails](AnimalConstants.AdoptionDetails)
    AnimalModel(id, url, animalTypeDetails, genderOpt, nameOpt, age, descriptionOpt, colorSet, 
        size, tagSet, isCastrated, imageOpt, videoOpt, adoptionDetailsOpt)
  }
  
}