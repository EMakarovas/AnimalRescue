package com.emakarovas.animalrescue.persistence.dao.impl

import com.emakarovas.animalrescue.model.relationship.User2PersonRelationship
import com.emakarovas.animalrescue.persistence.dao.User2PersonRelationshipDAO
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.mongo.User2PersonRelationshipCollectionType
import com.emakarovas.animalrescue.persistence.reader.User2PersonRelationshipReader
import com.emakarovas.animalrescue.persistence.writer.User2PersonRelationshipWriter

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultUser2PersonRelationshipDAO @Inject() (
    colFactory: MongoCollectionFactory,
    implicit val writer: User2PersonRelationshipWriter,
    implicit val reader: User2PersonRelationshipReader) extends User2PersonRelationshipDAO {
  
  import scala.concurrent.ExecutionContext.Implicits.global
  val collection = colFactory.getCollection(User2PersonRelationshipCollectionType)

}