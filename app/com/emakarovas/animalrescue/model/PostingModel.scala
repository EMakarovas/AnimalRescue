package com.emakarovas.animalrescue.model

import java.util.Date

case class PostingModel
  (override val id: String,
   startDate: Date,
   endDate: Option[Date],
   text: String,
   cost: CostModel,
   geolocation: GeolocationModel,
   userId: String)
   extends AbstractModel(id) {
  
}