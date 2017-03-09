package com.emakarovas.animalrescue.model

import com.emakarovas.animalrescue.model.enumeration.Country

case class LocationModel
  (override val id: String,
   country: String,
   city: String,
   street: Option[String],
   geolocation: GeolocationModel)
   extends AbstractModel(id)

case class GeolocationModel
  (longitude: Double,
   latitude: Double) extends AbstractEntity