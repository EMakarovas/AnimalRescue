package com.emakarovas.animalrescue.persistence.dao.impl

import com.emakarovas.animalrescue.persistence.dao.Posting2CostRelationshipDAO
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.mongo.Posting2CostRelationshipCollectionType
import com.emakarovas.animalrescue.persistence.reader.Posting2CostRelationshipReader
import com.emakarovas.animalrescue.persistence.writer.Posting2CostRelationshipWriter

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultPosting2CostRelationshipDAO @Inject() (
    colFactory: MongoCollectionFactory,
    implicit val writer: Posting2CostRelationshipWriter,
    implicit val reader: Posting2CostRelationshipReader) extends Posting2CostRelationshipDAO {
  
 val collection = colFactory.getCollection(Posting2CostRelationshipCollectionType)

}