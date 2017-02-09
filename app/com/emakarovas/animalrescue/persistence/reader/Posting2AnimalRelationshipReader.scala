package com.emakarovas.animalrescue.persistence.reader

import com.emakarovas.animalrescue.model.relationship.Posting2AnimalRelationship

import javax.inject.Singleton

@Singleton
class Posting2AnimalRelationshipReader extends AbstractRelationshipReader[Posting2AnimalRelationship] {

  def createInstance(id: String, sourceId: String, targetId: String): Posting2AnimalRelationship = {
    Posting2AnimalRelationship(id, sourceId, targetId)
  }
  
}