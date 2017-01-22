package com.emakarovas.animalrescue.persistence.reader

import com.emakarovas.animalrescue.model.GeolocationModel

import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class GeolocationReader extends AbstractModelReader[GeolocationModel] {
  
  def read(doc: BSONDocument): GeolocationModel = {
    val id = doc.getAs[String]("_id").get
    val latitude = doc.getAs[Double]("latitude").get
    val longitude = doc.getAs[Double]("longitude").get
    GeolocationModel(id, latitude, longitude)
  }
}

