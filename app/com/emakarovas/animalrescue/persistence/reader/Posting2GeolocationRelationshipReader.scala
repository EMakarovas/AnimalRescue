package com.emakarovas.animalrescue.persistence.reader

import com.emakarovas.animalrescue.model.relationship.Posting2GeolocationRelationship

import javax.inject.Singleton

@Singleton
class Posting2GeolocationRelationshipReader extends AbstractRelationshipReader[Posting2GeolocationRelationship] {

  def createInstance(id: String, sourceId: String, targetId: String): Posting2GeolocationRelationship = {
    Posting2GeolocationRelationship(id, sourceId, targetId)
  }
  
}