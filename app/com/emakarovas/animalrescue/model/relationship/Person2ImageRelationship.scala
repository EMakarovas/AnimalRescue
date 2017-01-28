package com.emakarovas.animalrescue.model.relationship

import com.emakarovas.animalrescue.model.ImageModel
import com.emakarovas.animalrescue.model.PersonModel

case class Person2ImageRelationship
  (override val id: String,
   override val sourceId: String,
   override val targetId: String) extends AbstractRelationship[PersonModel, ImageModel](id, sourceId, targetId) {
  
}