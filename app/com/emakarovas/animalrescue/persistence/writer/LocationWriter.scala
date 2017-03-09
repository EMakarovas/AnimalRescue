package com.emakarovas.animalrescue.persistence.writer

import com.emakarovas.animalrescue.model.GeolocationModel
import com.emakarovas.animalrescue.model.LocationModel
import com.emakarovas.animalrescue.model.constants.LocationConstants
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants
import com.emakarovas.animalrescue.persistence.dao.constants.MongoGeolocationConstants

import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class LocationWriter extends AbstractEntityWriter[LocationModel] {
  
  implicit object geolocationWriter extends AbstractEntityWriter[GeolocationModel] {
    override def write(geolocation: GeolocationModel): BSONDocument = {
      BSONDocument(
          MongoGeolocationConstants.Type -> "Point",
          MongoGeolocationConstants.Coordinates -> Seq(geolocation.longitude, geolocation.latitude))
    }
  }
  
  override def write(location: LocationModel): BSONDocument = {
    BSONDocument(
        MongoConstants.MongoId -> location.id,
        LocationConstants.Country -> location.country,
        LocationConstants.City -> location.city,
        LocationConstants.Street -> location.street,
        LocationConstants.Geolocation -> location.geolocation)
  }
  
}