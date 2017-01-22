package com.emakarovas.animalrescue.model

case class GeolocationModel
  (override val id: String,
   latitude: Double,
   longitude: Double)
   extends AbstractModel(id) {
  
}