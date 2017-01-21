package com.emakarovas.animalrescue.persistence.reader

import com.emakarovas.animalrescue.model.PersonModel

import reactivemongo.bson.BSONDocumentReader
import javax.inject.Singleton
import reactivemongo.bson.BSONDocument
import com.emakarovas.animalrescue.model.relationship.UserPersonRelationship

@Singleton
class UserPersonRelationshipReader extends BSONDocumentReader[UserPersonRelationship] {
  def read(doc: BSONDocument): UserPersonRelationship = {
    val userId = doc.getAs[String]("userId").get
    val personId = doc.getAs[String]("personId").get
    UserPersonRelationship(userId, personId)
  }
}