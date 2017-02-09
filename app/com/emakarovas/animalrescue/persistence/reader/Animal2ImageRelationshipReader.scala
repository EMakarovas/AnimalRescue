package com.emakarovas.animalrescue.persistence.reader

import com.emakarovas.animalrescue.model.relationship.Animal2ImageRelationship

import javax.inject.Singleton

@Singleton
class Animal2ImageRelationshipReader extends AbstractRelationshipReader[Animal2ImageRelationship] {

  def createInstance(id: String, sourceId: String, targetId: String): Animal2ImageRelationship = {
    Animal2ImageRelationship(id, sourceId, targetId)
  }
  
}