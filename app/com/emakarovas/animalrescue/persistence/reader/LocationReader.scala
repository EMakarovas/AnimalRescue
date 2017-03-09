package com.emakarovas.animalrescue.persistence.reader

import com.emakarovas.animalrescue.model.GeolocationModel
import com.emakarovas.animalrescue.model.LocationModel
import com.emakarovas.animalrescue.model.constants.LocationConstants
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants
import com.emakarovas.animalrescue.persistence.dao.constants.MongoGeolocationConstants

import javax.inject.Singleton
import reactivemongo.bson.BSONDocument
import reactivemongo.bson.BSONDocumentReader

@Singleton
class LocationReader extends AbstractEntityReader[LocationModel] {
  
  implicit object geolocationReader extends BSONDocumentReader[GeolocationModel] {
    override def read(doc: BSONDocument): GeolocationModel = {
      val coordinates = doc.getAs[Seq[Double]](MongoGeolocationConstants.Coordinates).get
      val longitude = coordinates(0)
      val latitude = coordinates(1)
      GeolocationModel(longitude, latitude)
    }
  }
  
  override def read(doc: BSONDocument): LocationModel = {
    val id = doc.getAs[String](MongoConstants.MongoId).get
    val country = doc.getAs[String](LocationConstants.Country).get
    val city = doc.getAs[String](LocationConstants.City).get
    val streetOpt = doc.getAs[String](LocationConstants.Street)
    val geolocation = doc.getAs[GeolocationModel](LocationConstants.Geolocation).get
    LocationModel(id, country, city, streetOpt, geolocation)
  }
}