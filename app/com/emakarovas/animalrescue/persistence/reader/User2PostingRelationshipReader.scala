package com.emakarovas.animalrescue.persistence.reader

import com.emakarovas.animalrescue.model.relationship.User2PostingRelationship

import javax.inject.Singleton

@Singleton
class User2PostingRelationshipReader extends AbstractRelationshipReader[User2PostingRelationship] {

  def createInstance(id: String, sourceId: String, targetId: String): User2PostingRelationship = {
    User2PostingRelationship(id, sourceId, targetId)
  }
  
}