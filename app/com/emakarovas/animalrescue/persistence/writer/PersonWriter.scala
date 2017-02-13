package com.emakarovas.animalrescue.persistence.writer

import com.emakarovas.animalrescue.model.PersonModel

import javax.inject.Singleton
import reactivemongo.bson.BSONDocument
import com.emakarovas.animalrescue.persistence.writer.enumeration.GenderWriter
import javax.inject.Inject

@Singleton
class PersonWriter @Inject() (
    implicit val genderWriter: GenderWriter,
    implicit val imageWriter: ImageWriter) extends AbstractModelWriter[PersonModel] {
  override def write(person: PersonModel): BSONDocument = {
    BSONDocument(
        "_id" -> person.id,
        "name" -> person.name,
        "surname" -> person.surname,
        "gender" -> person.gender,
        "image" -> person.image,
        "userId" -> person.userId)
  }
}