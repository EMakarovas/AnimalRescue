package com.emakarovas.animalrescue.persistence.writer

import com.emakarovas.animalrescue.model.AnimalModel

import javax.inject.Singleton
import reactivemongo.bson.BSONDocument
import reactivemongo.bson.BSONElement.converted
import com.emakarovas.animalrescue.persistence.writer.enumeration.GenderWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.AnimalTypeWriter
import javax.inject.Inject
import com.emakarovas.animalrescue.persistence.writer.option.IntOptionWriter
import com.emakarovas.animalrescue.persistence.writer.option.StringOptionWriter

@Singleton
class AnimalWriter @Inject() (
    implicit val animalTypeWriter: AnimalTypeWriter,
    implicit val genderWriter: GenderWriter,
    implicit val intOptionWriter: IntOptionWriter,
    implicit val stringOptionWriter: StringOptionWriter) extends AbstractModelWriter[AnimalModel] {

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