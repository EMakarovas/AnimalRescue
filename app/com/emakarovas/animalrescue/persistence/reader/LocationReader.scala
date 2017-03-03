package com.emakarovas.animalrescue.persistence.reader

import com.emakarovas.animalrescue.model.LocationModel
import com.emakarovas.animalrescue.model.constants.LocationConstants
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants

import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class LocationReader extends AbstractEntityReader[LocationModel] {
  
  override def read(doc: BSONDocument): LocationModel = {
    val id = doc.getAs[String](MongoConstants.MongoId).get
    val country = doc.getAs[String](LocationConstants.Country).get
    val city = doc.getAs[String](LocationConstants.City).get
    val streetOpt = doc.getAs[String](LocationConstants.Street)
    val latitude = doc.getAs[Double](LocationConstants.Latitude).get
    val longitude = doc.getAs[Double](LocationConstants.Longitude).get
    LocationModel(id, country, city, streetOpt, latitude, longitude)
  }
}