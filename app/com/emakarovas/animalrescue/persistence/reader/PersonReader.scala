package com.emakarovas.animalrescue.persistence.reader

import com.emakarovas.animalrescue.model.PersonModel
import com.emakarovas.animalrescue.model.common.Gender
import com.emakarovas.animalrescue.persistence.reader.enumeration.GenderReader

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class PersonReader @Inject
  (implicit genderReader: GenderReader) extends AbstractModelReader[PersonModel] {
  def read(doc: BSONDocument): PersonModel = {
    val id = doc.getAs[String]("_id").get
    val name = doc.getAs[String]("name").get
    val surname = doc.getAs[String]("surname").get
    val gender = doc.getAs[Gender.Value]("gender").get
    PersonModel(id, name, surname, gender)
  }
}