package com.emakarovas.animalrescue.persistence.writer

import com.emakarovas.animalrescue.model.relationship.AbstractRelationship

import reactivemongo.bson.BSONDocument
import reactivemongo.bson.BSONDocumentWriter

abstract class AbstractRelationshipWriter[T <: AbstractRelationship[_, _]] extends BSONDocumentWriter[T] {
  def write(rel: T): BSONDocument = {
    BSONDocument(
        "_id" -> rel.id,
        "sourceId" -> rel.sourceId,
        "targetId" -> rel.targetId)
  }
}