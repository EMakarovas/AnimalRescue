package com.emakarovas.animalrescue.model.relationship

import com.emakarovas.animalrescue.model.PostingModel
import com.emakarovas.animalrescue.model.CostModel

case class Posting2CostRelationship
  (override val id: String,
   override val sourceId: String,
   override val targetId: String) extends AbstractRelationship[PostingModel, CostModel](id, sourceId, targetId) {
  
}