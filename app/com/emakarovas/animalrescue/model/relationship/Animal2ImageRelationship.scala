package com.emakarovas.animalrescue.model.relationship

import com.emakarovas.animalrescue.model.ImageModel
import com.emakarovas.animalrescue.model.AnimalModel

case class Animal2ImageRelationship
  (override val id: String,
   override val sourceId: String,
   override val targetId: String) extends AbstractRelationship[AnimalModel, ImageModel](id, sourceId, targetId) {
  
}