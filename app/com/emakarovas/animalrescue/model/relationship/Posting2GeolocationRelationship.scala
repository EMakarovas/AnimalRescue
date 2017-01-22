package com.emakarovas.animalrescue.model.relationship

import com.emakarovas.animalrescue.model.PostingModel
import com.emakarovas.animalrescue.model.GeolocationModel

case class Posting2GeolocationRelationship
  (override val id: String,
   override val sourceId: String,
   override val targetId: String) extends AbstractRelationship[PostingModel, GeolocationModel](id, sourceId, targetId) {
  
}