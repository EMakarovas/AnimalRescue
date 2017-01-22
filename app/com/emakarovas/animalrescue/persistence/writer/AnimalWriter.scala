package com.emakarovas.animalrescue.persistence.writer

import com.emakarovas.animalrescue.model.AnimalModel

import javax.inject.Singleton
import reactivemongo.bson.BSONDocument
import reactivemongo.bson.BSONElement.converted

@Singleton
class AnimalWriter extends AbstractModelWriter[AnimalModel] {
  override def write(animal: AnimalModel): BSONDocument = {
    BSONDocument(
        "_id" -> animal.id,
        "animalType" -> animal.animalType.toString,
        "specificType" -> animal.specificType,
        "name" -> animal.name,
        "gender" -> animal.gender.toString,
        "age" -> animal.age,
        "description" -> animal.description)
  }
}