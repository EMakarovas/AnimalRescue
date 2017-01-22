package com.emakarovas.animalrescue.persistence.writer

import com.emakarovas.animalrescue.model.PersonModel

import reactivemongo.bson.BSONDocument
import reactivemongo.bson.BSONDocumentWriter
import reactivemongo.bson.BSONElement.converted

import javax.inject.Singleton
import com.emakarovas.animalrescue.model.relationship.User2PersonRelationship

@Singleton
class User2PersonRelationshipWriter extends AbstractRelationshipWriter[User2PersonRelationship] {

}