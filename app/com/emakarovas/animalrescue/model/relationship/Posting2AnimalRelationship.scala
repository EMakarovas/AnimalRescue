package com.emakarovas.animalrescue.model.relationship

import com.emakarovas.animalrescue.model.PostingModel
import com.emakarovas.animalrescue.model.AnimalModel

case class Posting2AnimalRelationship
  (override val id: String,
   override val sourceId: String,
   override val targetId: String) extends AbstractRelationship[PostingModel, AnimalModel](id, sourceId, targetId) {
  
}