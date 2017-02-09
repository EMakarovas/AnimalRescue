package com.emakarovas.animalrescue.persistence.writer

import com.emakarovas.animalrescue.model.PersonModel

import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class PersonWriter extends AbstractModelWriter[PersonModel] {
  override def write(person: PersonModel): BSONDocument = {
    BSONDocument(
        "_id" -> person.id,
        "name" -> person.name,
        "surname" -> person.surname,
        "gender" -> person.gender.toString())
  }
}