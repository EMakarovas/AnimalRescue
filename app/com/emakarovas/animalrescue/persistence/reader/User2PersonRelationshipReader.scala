package com.emakarovas.animalrescue.persistence.reader

import com.emakarovas.animalrescue.model.relationship.User2PersonRelationship

import javax.inject.Singleton

@Singleton
class User2PersonRelationshipReader extends AbstractRelationshipReader[User2PersonRelationship] {

  def createInstance(id: String, sourceId: String, targetId: String): User2PersonRelationship = {
    User2PersonRelationship(id, sourceId, targetId)
  }
  
}