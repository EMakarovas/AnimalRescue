package com.emakarovas.animalrescue.persistence.reader

import com.emakarovas.animalrescue.model.PostingAnimalModel
import com.emakarovas.animalrescue.model.enumeration.AnimalType
import com.emakarovas.animalrescue.model.enumeration.Gender
import com.emakarovas.animalrescue.persistence.reader.enumeration.AnimalTypeReader
import com.emakarovas.animalrescue.persistence.reader.enumeration.GenderReader

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.bson.BSONDocument
import com.emakarovas.animalrescue.model.ImageModel

@Singleton
class PostingAnimalReader @Inject() 
  (implicit val animalTypeReader: AnimalTypeReader,
   implicit val genderReader: GenderReader,
   implicit val imageReader: ImageReader) extends AbstractModelReader[PostingAnimalModel] {
  override def read(doc: BSONDocument): PostingAnimalModel = {
    val id = doc.getAs[String]("_id").get
    val animalType = doc.getAs[AnimalType]("animalType").get
    val specificTypeOpt = doc.getAs[String]("specificType")
    val nameOpt = doc.getAs[String]("name")
    val gender = doc.getAs[Gender]("gender").get
    val ageOpt = doc.getAs[Int]("age")
    val descriptionOpt = doc.getAs[String]("description")
    val imageOpt = doc.getAs[ImageModel]("image")
    val postingId = doc.getAs[String]("postingId").get
    PostingAnimalModel(id, animalType, specificTypeOpt, nameOpt, gender, ageOpt, descriptionOpt, imageOpt, postingId)
  }
}