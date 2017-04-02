package com.emakarovas.animalrescue.persistence.writer

import com.emakarovas.animalrescue.model.Geolocation
import com.emakarovas.animalrescue.model.Location
import com.emakarovas.animalrescue.model.constants.LocationConstants
import com.emakarovas.animalrescue.persistence.dao.constants.MongoGeolocationConstants

import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class LocationWriter extends AbstractEntityWriter[Location] {
  
  implicit object geolocationWriter extends AbstractEntityWriter[Geolocation] {
    override def write(geolocation: Geolocation): BSONDocument = {
      BSONDocument(
          MongoGeolocationConstants.Type -> "Point",
          MongoGeolocationConstants.Coordinates -> Seq(geolocation.longitude, geolocation.latitude))
    }
  }
  
  override def write(location: Location): BSONDocument = {
    BSONDocument(
        LocationConstants.Country -> location.country,
        LocationConstants.City -> location.city,
        LocationConstants.Street -> location.street,
        LocationConstants.Geolocation -> location.geolocation)
  }
  
}