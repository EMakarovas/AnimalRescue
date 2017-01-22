package com.emakarovas.animalrescue.persistence.dao.impl

import com.emakarovas.animalrescue.persistence.dao.PersonDAO
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.mongo.PersonModelCollectionType
import com.emakarovas.animalrescue.persistence.reader.PersonReader
import com.emakarovas.animalrescue.persistence.writer.PersonWriter

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultPersonDAO @Inject() (
    colFactory: MongoCollectionFactory,
    implicit val writer: PersonWriter,
    implicit val reader: PersonReader) extends PersonDAO {
  
 val collection = colFactory.getCollection(PersonModelCollectionType)
  
}