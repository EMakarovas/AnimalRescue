package com.emakarovas.animalrescue.persistence.reader

import com.emakarovas.animalrescue.model.PersonModel
import com.emakarovas.animalrescue.model.common.Gender

import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class PersonReader extends AbstractModelReader[PersonModel] {
  def read(doc: BSONDocument): PersonModel = {
    val id = doc.getAs[String]("_id").get
    val name = doc.getAs[String]("name").get
    val surname = doc.getAs[String]("surname").get
    val genderStr = doc.getAs[String]("gender").get
    val gender = Gender withName genderStr
    PersonModel(id, name, surname, gender)
  }
}