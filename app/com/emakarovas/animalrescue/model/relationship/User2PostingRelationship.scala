package com.emakarovas.animalrescue.model.relationship

import com.emakarovas.animalrescue.model.PostingModel
import com.emakarovas.animalrescue.model.UserModel

case class User2PostingRelationship
  (override val id: String,
   override val sourceId: String,
   override val targetId: String) extends AbstractRelationship[UserModel, PostingModel](id, sourceId, targetId) {
  
}