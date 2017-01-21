package com.emakarovas.animalrescue.persistence.dao

import com.emakarovas.animalrescue.model.relationship.AbstractRelationship

trait AbstractRelationshipDAO[T <: AbstractRelationship] extends AbstractDAO[T] {
  
}