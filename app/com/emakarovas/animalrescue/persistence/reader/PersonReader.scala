package com.emakarovas.animalrescue.persistence.reader

import com.emakarovas.animalrescue.model.ImageModel
import com.emakarovas.animalrescue.model.PersonModel
import com.emakarovas.animalrescue.model.enumeration.Gender
import com.emakarovas.animalrescue.persistence.reader.enumeration.GenderReader

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class PersonReader @Inject() (
    implicit val genderReader: GenderReader,
    implicit val imageReader: ImageReader) extends AbstractModelReader[PersonModel] {
  override def read(doc: BSONDocument): PersonModel = {
    val id = doc.getAs[String]("_id").get
    val name = doc.getAs[String]("name").get
    val surname = doc.getAs[String]("surname").get
    val gender = doc.getAs[Gender]("gender").get
    val imageOpt = doc.getAs[ImageModel]("image")
    val userId = doc.getAs[String]("userId").get
    PersonModel(id, name, surname, gender, imageOpt, userId)
  }
}