package com.emakarovas.animalrescue.persistence.reader

import com.emakarovas.animalrescue.model.AnimalModel
import com.emakarovas.animalrescue.model.animal.AnimalType
import com.emakarovas.animalrescue.model.common.Gender

import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class AnimalReader extends AbstractModelReader[AnimalModel] {
  def read(doc: BSONDocument): AnimalModel = {
    val id = doc.getAs[String]("_id").get
    val animalTypeStr = doc.getAs[String]("animalType").get
    val animalType = AnimalType withName animalTypeStr
    val specificTypeOpt = doc.getAs[String]("specificType")
    val nameOpt = doc.getAs[String]("name")
    val genderStr = doc.getAs[String]("gender").get
    val gender = Gender withName genderStr
    val ageOpt = doc.getAs[Int]("age")
    val descriptionOpt = doc.getAs[String]("description")
    AnimalModel(id, animalType, specificTypeOpt, nameOpt, gender, ageOpt, descriptionOpt)
  }
}