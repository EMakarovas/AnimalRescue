package com.emakarovas.animalrescue.persistence.writer

import com.emakarovas.animalrescue.model.AnimalTypeDetails
import com.emakarovas.animalrescue.model.enumeration.Animal
import com.emakarovas.animalrescue.persistence.writer.enumeration.AnimalTypeWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.SpecificTypeWriter

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.bson.BSONDocument
import com.emakarovas.animalrescue.model.constants.AnimalTypeDetailsConstants

@Singleton
class AnimalTypeDetailsWriter @Inject() (
    implicit val animalTypeWriter: AnimalTypeWriter,
    implicit val specificTypeWriter: SpecificTypeWriter) extends AbstractEntityWriter[AnimalTypeDetails[_ <: Animal]] {
  override def write(animalTypeDetails: AnimalTypeDetails[_ <: Animal]): BSONDocument = {
    BSONDocument(
        AnimalTypeDetailsConstants.AnimalType -> animalTypeWriter.write(animalTypeDetails.animalType),
        AnimalTypeDetailsConstants.SpecificTypeSet -> specificTypeWriter.writeCollection(animalTypeDetails.specificTypeSet))
  }
}