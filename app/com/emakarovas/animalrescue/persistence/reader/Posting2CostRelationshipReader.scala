package com.emakarovas.animalrescue.persistence.reader

import com.emakarovas.animalrescue.model.relationship.Posting2CostRelationship

import javax.inject.Singleton

@Singleton
class Posting2CostRelationshipReader extends AbstractRelationshipReader[Posting2CostRelationship] {

  def createInstance(id: String, sourceId: String, targetId: String): Posting2CostRelationship = {
    Posting2CostRelationship(id, sourceId, targetId)
  }
  
}