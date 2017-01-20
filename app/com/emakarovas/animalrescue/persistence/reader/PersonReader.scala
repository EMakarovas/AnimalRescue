package com.emakarovas.animalrescue.persistence.reader

import com.emakarovas.animalrescue.model.PersonModel

import reactivemongo.bson.BSONDocumentReader
import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class PersonReader extends BSONDocumentReader[PersonModel] {
  def read(doc: BSONDocument): PersonModel = {
    val id = doc.getAs[String]("_id").get
    val name = doc.getAs[String]("name").get
    val surname = doc.getAs[String]("surname").get
    PersonModel(id, name, surname)
  }
}