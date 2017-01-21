package com.emakarovas.animalrescue.persistence.writer

import com.emakarovas.animalrescue.model.PersonModel

import reactivemongo.bson.BSONDocument
import reactivemongo.bson.BSONDocumentWriter
import reactivemongo.bson.BSONElement.converted

import javax.inject.Singleton
import com.emakarovas.animalrescue.model.relationship.UserPersonRelationship

@Singleton
class UserPersonRelationshipWriter extends BSONDocumentWriter[UserPersonRelationship] {
  def write(rel: UserPersonRelationship): BSONDocument = {
    BSONDocument(
        "userId" -> rel.userId,
        "personId" -> rel.personId)
  }
}