package com.emakarovas.animalrescue.model

import com.emakarovas.animalrescue.model.enumeration.Country

case class Location
  (country: String,
   city: String,
   street: Option[String],
   geolocation: Geolocation)
   extends AbstractEntity

case class Geolocation
  (longitude: Double,
   latitude: Double) extends AbstractEntity