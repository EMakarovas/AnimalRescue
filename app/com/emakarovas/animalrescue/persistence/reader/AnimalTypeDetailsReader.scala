package com.emakarovas.animalrescue.persistence.reader

import com.emakarovas.animalrescue.model.AnimalTypeDetails
import com.emakarovas.animalrescue.model.constants.AnimalTypeDetailsConstants
import com.emakarovas.animalrescue.model.enumeration.Animal
import com.emakarovas.animalrescue.model.enumeration.AnimalType
import com.emakarovas.animalrescue.model.enumeration.SpecificType
import com.emakarovas.animalrescue.persistence.reader.enumeration.AnimalTypeReader
import com.emakarovas.animalrescue.persistence.reader.enumeration.SpecificTypeReader

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class AnimalTypeDetailsReader @Inject() (
    implicit val animalTypeReader: AnimalTypeReader,
    implicit val specificTypeReader: SpecificTypeReader) extends AbstractEntityReader[AnimalTypeDetails[_ <: Animal]] {
  override def read(doc: BSONDocument): AnimalTypeDetails[_ <: Animal] = {
    val animalType = doc.getAs[AnimalType[_ <: Animal]](AnimalTypeDetailsConstants.AnimalType).get
    val specificTypeSet = doc.getAs[Set[SpecificType[Animal]]](AnimalTypeDetailsConstants.SpecificTypeSet).get
    AnimalTypeDetails[Animal](animalType, specificTypeSet)
  }
}