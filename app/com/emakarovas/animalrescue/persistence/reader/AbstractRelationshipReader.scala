package com.emakarovas.animalrescue.persistence.reader

import com.emakarovas.animalrescue.model.relationship.AbstractRelationship

import reactivemongo.bson.BSONDocument
import reactivemongo.bson.BSONDocumentReader

abstract class AbstractRelationshipReader[T <: AbstractRelationship[_, _]] extends BSONDocumentReader[T] {
  
  def read(doc: BSONDocument): T = {
    val id = doc.getAs[String]("_id").get
    val sourceId = doc.getAs[String]("sourceId").get
    val targetId = doc.getAs[String]("targetId").get
    createInstance(id, sourceId, targetId)
  }
  
  def createInstance(id: String, sourceId: String, targetId: String): T
  
}