package com.emakarovas.animalrescue.persistence.dao.impl

import com.emakarovas.animalrescue.model.relationship.User2PersonRelationship
import com.emakarovas.animalrescue.persistence.dao.User2PersonRelationshipDAO
import com.emakarovas.animalrescue.persistence.mongo.Mongo

import javax.inject._
import com.emakarovas.animalrescue.persistence.writer.User2PersonRelationshipWriter
import com.emakarovas.animalrescue.persistence.reader.User2PersonRelationshipReader
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.mongo.User2PersonRelationshipCollectionType

@Singleton
class DefaultUser2PersonRelationshipDAO @Inject() (
    colFactory: MongoCollectionFactory,
    implicit val user2PersonRelationshipWriter: User2PersonRelationshipWriter,
    implicit val user2PersonRelationshipReader: User2PersonRelationshipReader) extends User2PersonRelationshipDAO {
  
  import scala.concurrent.ExecutionContext.Implicits.global
  private val collection = colFactory.getCollection(User2PersonRelationshipCollectionType)
  
  override def create(rel: User2PersonRelationship) = collection.flatMap(_.insert(rel)).map(writeRes => writeRes.n)
  
}