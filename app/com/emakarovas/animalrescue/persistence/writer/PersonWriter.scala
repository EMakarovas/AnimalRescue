package com.emakarovas.animalrescue.persistence.writer

import com.emakarovas.animalrescue.model.PersonModel

import reactivemongo.bson.BSONDocument
import reactivemongo.bson.BSONDocumentWriter
import reactivemongo.bson.BSONElement.converted

import javax.inject.Singleton

@Singleton
class PersonWriter extends BSONDocumentWriter[PersonModel] {
  def write(person: PersonModel): BSONDocument = {
    BSONDocument(
        "_id" -> person.id,
        "name" -> person.name,
        "surname" -> person.surname)
  }
}