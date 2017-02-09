package com.emakarovas.animalrescue.persistence.dao.impl

import com.emakarovas.animalrescue.persistence.dao.Posting2AnimalRelationshipDAO
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.mongo.Posting2AnimalRelationshipCollectionType
import com.emakarovas.animalrescue.persistence.reader.Posting2AnimalRelationshipReader
import com.emakarovas.animalrescue.persistence.writer.Posting2AnimalRelationshipWriter

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultPosting2AnimalRelationshipDAO @Inject() (
    colFactory: MongoCollectionFactory,
    implicit val writer: Posting2AnimalRelationshipWriter,
    implicit val reader: Posting2AnimalRelationshipReader) extends Posting2AnimalRelationshipDAO {
  
 val collection = colFactory.getCollection(Posting2AnimalRelationshipCollectionType)

}