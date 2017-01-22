package com.emakarovas.animalrescue.persistence.dao

import com.emakarovas.animalrescue.model.relationship.AbstractRelationship
import scala.concurrent.Future

trait AbstractRelationshipDAO[T <: AbstractRelationship[_, _]] extends AbstractDAO[T] {
  def findBySourceId(sourceId: String): Future[List[T]]
  def findByTargetId(targetId: String): Future[List[T]]
}