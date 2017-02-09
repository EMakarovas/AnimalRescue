package com.emakarovas.animalrescue.persistence.dao.impl

import com.emakarovas.animalrescue.persistence.dao.Posting2GeolocationRelationshipDAO
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.mongo.Posting2GeolocationRelationshipCollectionType
import com.emakarovas.animalrescue.persistence.reader.Posting2GeolocationRelationshipReader
import com.emakarovas.animalrescue.persistence.writer.Posting2GeolocationRelationshipWriter

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultPosting2GeolocationRelationshipDAO @Inject() (
    colFactory: MongoCollectionFactory,
    implicit val writer: Posting2GeolocationRelationshipWriter,
    implicit val reader: Posting2GeolocationRelationshipReader) extends Posting2GeolocationRelationshipDAO {
  
 val collection = colFactory.getCollection(Posting2GeolocationRelationshipCollectionType)

}