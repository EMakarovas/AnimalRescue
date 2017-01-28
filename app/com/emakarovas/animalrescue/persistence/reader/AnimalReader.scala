package com.emakarovas.animalrescue.persistence.reader

import com.emakarovas.animalrescue.model.AnimalModel
import com.emakarovas.animalrescue.model.enumeration.AnimalType
import com.emakarovas.animalrescue.model.enumeration.Gender
import com.emakarovas.animalrescue.persistence.reader.enumeration.AnimalTypeReader

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.bson.BSONDocument
import com.emakarovas.animalrescue.persistence.reader.enumeration.GenderReader
import reactivemongo.bson.BSONInteger
import reactivemongo.bson.BSONString
import com.emakarovas.animalrescue.persistence.reader.option.IntOptionReader
import com.emakarovas.animalrescue.persistence.reader.option.StringOptionReader

@Singleton
class AnimalReader @Inject() 
  (implicit var animalTypeReader: AnimalTypeReader,
   implicit var genderReader: GenderReader,
   implicit var intOptionReader: IntOptionReader,
   implicit var stringOptionReader: StringOptionReader) extends AbstractModelReader[AnimalModel] {
  def read(doc: BSONDocument): AnimalModel = {
    val id = doc.getAs[String]("_id").get
    val animalType = doc.getAs[AnimalType]("animalType").get
    val specificTypeOpt = doc.getAs[String]("specificType")
    val nameOpt = doc.getAs[String]("name")
    val gender = doc.getAs[Gender]("gender").get
    val ageOpt = doc.getAs[Int]("age")
    val descriptionOpt = doc.getAs[String]("description")
    AnimalModel(id, animalType, specificTypeOpt, nameOpt, gender, ageOpt, descriptionOpt)
  }
}