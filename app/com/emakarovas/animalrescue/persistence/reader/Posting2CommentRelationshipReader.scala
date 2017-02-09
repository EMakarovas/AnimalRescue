package com.emakarovas.animalrescue.persistence.reader

import com.emakarovas.animalrescue.model.relationship.Posting2CommentRelationship

import javax.inject.Singleton

@Singleton
class Posting2CommentRelationshipReader extends AbstractRelationshipReader[Posting2CommentRelationship] {

  def createInstance(id: String, sourceId: String, targetId: String): Posting2CommentRelationship = {
    Posting2CommentRelationship(id, sourceId, targetId)
  }
  
}