package com.emakarovas.animalrescue.model

import com.emakarovas.animalrescue.model.enumeration.Country

case class LocationModel
  (override val id: String,
   country: String,
   city: String,
   street: Option[String],
   latitude: Double,
   longitude: Double)
   extends AbstractModel(id)