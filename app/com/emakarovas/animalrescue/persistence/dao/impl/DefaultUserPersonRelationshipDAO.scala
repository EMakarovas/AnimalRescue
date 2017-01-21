package com.emakarovas.animalrescue.persistence.dao.impl

import com.emakarovas.animalrescue.model.relationship.UserPersonRelationship
import com.emakarovas.animalrescue.persistence.dao.UserPersonRelationshipDAO
import com.emakarovas.animalrescue.persistence.mongo.Mongo

import javax.inject._
import com.emakarovas.animalrescue.persistence.writer.UserPersonRelationshipWriter
import com.emakarovas.animalrescue.persistence.reader.UserPersonRelationshipReader
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.mongo.UserPersonRelationshipCollectionType

@Singleton
class DefaultUserPersonRelationshipDAO @Inject() (
    colFactory: MongoCollectionFactory,
    implicit val userPersonRelationshipWriter: UserPersonRelationshipWriter,
    implicit val userPersonRelationshipReader: UserPersonRelationshipReader) extends UserPersonRelationshipDAO {
  
  import scala.concurrent.ExecutionContext.Implicits.global
  private val collection = colFactory.getCollection(UserPersonRelationshipCollectionType)
  
  override def create(rel: UserPersonRelationship) = collection.flatMap(_.insert(rel)).map(writeRes => writeRes.n)
  
}