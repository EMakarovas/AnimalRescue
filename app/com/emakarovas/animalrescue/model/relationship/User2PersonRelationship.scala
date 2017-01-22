package com.emakarovas.animalrescue.model.relationship

import com.emakarovas.animalrescue.model.PersonModel
import com.emakarovas.animalrescue.model.UserModel

case class User2PersonRelationship
  (override val id: String,
   override val sourceId: String,
   override val targetId: String) extends AbstractRelationship[UserModel, PersonModel](id, sourceId, targetId) {
  
}