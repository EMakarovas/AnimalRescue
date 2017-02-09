package com.emakarovas.animalrescue.persistence.writer

import com.emakarovas.animalrescue.model.GeolocationModel

import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class GeolocationWriter extends AbstractModelWriter[GeolocationModel] {
  override def write(geolocation: GeolocationModel): BSONDocument = {
    BSONDocument(
        "_id" -> geolocation.id,
        "latitude" -> geolocation.latitude,
        "longitude" -> geolocation.longitude)
  }
}