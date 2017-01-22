package com.emakarovas.animalrescue.persistence.writer

import com.emakarovas.animalrescue.model.PersonModel

import reactivemongo.bson.BSONDocument
import reactivemongo.bson.BSONDocumentWriter
import reactivemongo.bson.BSONElement.converted

import javax.inject.Singleton
import com.emakarovas.animalrescue.model.relationship.User2PersonRelationship
import com.emakarovas.animalrescue.model.relationship.AbstractRelationship

abstract class AbstractRelationshipWriter[T <: AbstractRelationship[_, _]] extends BSONDocumentWriter[T] {
  def write(rel: T): BSONDocument = {
    BSONDocument(
        "_id" -> rel.id,
        "sourceId" -> rel.sourceId,
        "targetId" -> rel.targetId)
  }
}