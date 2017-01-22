package com.emakarovas.animalrescue.persistence.reader

import com.emakarovas.animalrescue.model.AnimalModel
import com.emakarovas.animalrescue.model.animal.AnimalType
import com.emakarovas.animalrescue.model.common.Gender
import com.emakarovas.animalrescue.persistence.reader.enumeration.AnimalTypeReader

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.bson.BSONDocument
import com.emakarovas.animalrescue.persistence.reader.enumeration.GenderReader

@Singleton
class AnimalReader @Inject() 
  (implicit var animalTypeReader: AnimalTypeReader,
   implicit var genderReader: GenderReader) extends AbstractModelReader[AnimalModel] {
  def read(doc: BSONDocument): AnimalModel = {
    val id = doc.getAs[String]("_id").get
    val animalType = doc.getAs[AnimalType.Value]("animalType").get
    val specificTypeOpt = doc.getAs[String]("specificType")
    val nameOpt = doc.getAs[String]("name")
    val gender = doc.getAs[Gender.Value]("gender").get
    val ageOpt = doc.getAs[Int]("age")
    val descriptionOpt = doc.getAs[String]("description")
    println("######################################")
    println(ageOpt)
    println(AnimalModel(id, animalType, specificTypeOpt, nameOpt, gender, ageOpt, descriptionOpt))
    AnimalModel(id, animalType, specificTypeOpt, nameOpt, gender, ageOpt, descriptionOpt)
  }
}