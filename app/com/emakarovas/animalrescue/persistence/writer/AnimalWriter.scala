package com.emakarovas.animalrescue.persistence.writer

import com.emakarovas.animalrescue.model.AnimalModel

import javax.inject.Singleton
import reactivemongo.bson.BSONDocument
import reactivemongo.bson.BSONElement.converted
import com.emakarovas.animalrescue.persistence.writer.enumeration.ModelEnumerationWriter
import javax.inject.Inject

@Singleton
class AnimalWriter @Inject() 
  (implicit modelEnumerationWriter: ModelEnumerationWriter) extends AbstractModelWriter[AnimalModel] {
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