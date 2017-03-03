package com.emakarovas.animalrescue.persistence.writer

import com.emakarovas.animalrescue.model.LocationModel
import com.emakarovas.animalrescue.model.constants.LocationConstants
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants

import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class LocationWriter extends AbstractEntityWriter[LocationModel] {
  override def write(location: LocationModel): BSONDocument = {
    BSONDocument(
        MongoConstants.MongoId -> location.id,
        LocationConstants.Country -> location.country,
        LocationConstants.City -> location.city,
        LocationConstants.Street -> location.street,
        LocationConstants.Latitude -> location.latitude,
        LocationConstants.Longitude -> location.longitude)
  }
}