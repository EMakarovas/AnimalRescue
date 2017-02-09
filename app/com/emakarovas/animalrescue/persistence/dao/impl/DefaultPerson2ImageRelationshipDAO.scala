package com.emakarovas.animalrescue.persistence.dao.impl

import com.emakarovas.animalrescue.persistence.dao.Person2ImageRelationshipDAO
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.mongo.Person2ImageRelationshipCollectionType
import com.emakarovas.animalrescue.persistence.reader.Person2ImageRelationshipReader
import com.emakarovas.animalrescue.persistence.writer.Person2ImageRelationshipWriter

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultPerson2ImageRelationshipDAO @Inject() (
    colFactory: MongoCollectionFactory,
    implicit val writer: Person2ImageRelationshipWriter,
    implicit val reader: Person2ImageRelationshipReader) extends Person2ImageRelationshipDAO {
  
 val collection = colFactory.getCollection(Person2ImageRelationshipCollectionType)

}