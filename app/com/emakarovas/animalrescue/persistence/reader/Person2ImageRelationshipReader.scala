package com.emakarovas.animalrescue.persistence.reader

import com.emakarovas.animalrescue.model.relationship.Person2ImageRelationship

import javax.inject.Singleton

@Singleton
class Person2ImageRelationshipReader extends AbstractRelationshipReader[Person2ImageRelationship] {

  def createInstance(id: String, sourceId: String, targetId: String): Person2ImageRelationship = {
    Person2ImageRelationship(id, sourceId, targetId)
  }
  
}