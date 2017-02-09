package com.emakarovas.animalrescue.persistence.dao.impl

import com.emakarovas.animalrescue.persistence.dao.User2PostingRelationshipDAO
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.mongo.User2PostingRelationshipCollectionType
import com.emakarovas.animalrescue.persistence.reader.User2PostingRelationshipReader
import com.emakarovas.animalrescue.persistence.writer.User2PostingRelationshipWriter

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultUser2PostingRelationshipDAO @Inject() (
    colFactory: MongoCollectionFactory,
    implicit val writer: User2PostingRelationshipWriter,
    implicit val reader: User2PostingRelationshipReader) extends User2PostingRelationshipDAO {
  
 val collection = colFactory.getCollection(User2PostingRelationshipCollectionType)

}