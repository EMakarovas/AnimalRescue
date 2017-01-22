package com.emakarovas.animalrescue.model.relationship

import com.emakarovas.animalrescue.model.AbstractEntity

abstract class AbstractRelationship[T, U] (
    override val id: String,
    val sourceId: String,
    val targetId: String) extends AbstractEntity(id) {
  
}