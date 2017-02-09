package com.emakarovas.animalrescue.persistence.writer

import com.emakarovas.animalrescue.model.AnimalModel
import com.emakarovas.animalrescue.persistence.writer.enumeration.AnimalTypeWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.GenderWriter

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class AnimalWriter @Inject() (
    implicit val animalTypeWriter: AnimalTypeWriter,
    implicit val genderWriter: GenderWriter) extends AbstractModelWriter[AnimalModel] {

  override def write(animal: AnimalModel): BSONDocument = {
    BSONDocument(
        "_id" -> animal.id,
        "animalType" -> animal.animalType,
        "specificType" -> animal.specificType,
        "name" -> animal.name,
        "gender" -> animal.gender,
        "age" -> animal.age,
        "description" -> animal.description)
  }
}