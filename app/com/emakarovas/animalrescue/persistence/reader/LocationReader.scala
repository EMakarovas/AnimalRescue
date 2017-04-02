package com.emakarovas.animalrescue.persistence.reader

import com.emakarovas.animalrescue.model.Geolocation
import com.emakarovas.animalrescue.model.Location
import com.emakarovas.animalrescue.model.constants.LocationConstants
import com.emakarovas.animalrescue.persistence.dao.constants.MongoGeolocationConstants

import javax.inject.Singleton
import reactivemongo.bson.BSONDocument
import reactivemongo.bson.BSONDocumentReader

@Singleton
class LocationReader extends AbstractEntityReader[Location] {
  
  implicit object geolocationReader extends BSONDocumentReader[Geolocation] {
    override def read(doc: BSONDocument): Geolocation = {
      val coordinates = doc.getAs[Seq[Double]](MongoGeolocationConstants.Coordinates).get
      val longitude = coordinates(0)
      val latitude = coordinates(1)
      Geolocation(longitude, latitude)
    }
  }
  
  override def read(doc: BSONDocument): Location = {
    val country = doc.getAs[String](LocationConstants.Country).get
    val city = doc.getAs[String](LocationConstants.City).get
    val streetOpt = doc.getAs[String](LocationConstants.Street)
    val geolocation = doc.getAs[Geolocation](LocationConstants.Geolocation).get
    Location(country, city, streetOpt, geolocation)
  }
}