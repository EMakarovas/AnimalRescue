package com.emakarovas.animalrescue.persistence.reader

import java.util.Date

import com.emakarovas.animalrescue.model.GeolocationModel
import com.emakarovas.animalrescue.model.WishModel
import com.emakarovas.animalrescue.model.enumeration.AnimalType
import com.emakarovas.animalrescue.model.enumeration.Gender
import com.emakarovas.animalrescue.persistence.reader.enumeration.AnimalTypeReader
import com.emakarovas.animalrescue.persistence.reader.enumeration.GenderReader

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class WishReader @Inject() (
    implicit val animalTypeReader: AnimalTypeReader,
    implicit val genderReader: GenderReader,
    implicit val geolocationReader: GeolocationReader) extends AbstractModelReader[WishModel] {
  def read(doc: BSONDocument): WishModel = {
    val id = doc.getAs[String]("_id").get
    val animalType = doc.getAs[AnimalType]("animalType").get
    val specificType = doc.getAs[String]("specificType")
    val gender = doc.getAs[Gender]("gender").get
    val minAge = doc.getAs[Int]("minAge")
    val maxAge = doc.getAs[Int]("maxAge")
    val geolocation = doc.getAs[GeolocationModel]("geolocation").get
    val startDate = doc.getAs[Date]("startDate").get
    val endDate = doc.getAs[Date]("endDate")
    val userId = doc.getAs[String]("userId").get
    WishModel(id, animalType, specificType, gender, minAge, maxAge, geolocation, startDate, endDate, userId)
  }
}