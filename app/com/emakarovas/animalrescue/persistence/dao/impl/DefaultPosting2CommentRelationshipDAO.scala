package com.emakarovas.animalrescue.persistence.dao.impl

import com.emakarovas.animalrescue.persistence.dao.Posting2CommentRelationshipDAO
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.mongo.Posting2CommentRelationshipCollectionType
import com.emakarovas.animalrescue.persistence.reader.Posting2CommentRelationshipReader
import com.emakarovas.animalrescue.persistence.writer.Posting2CommentRelationshipWriter

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultPosting2CommentRelationshipDAO @Inject() (
    colFactory: MongoCollectionFactory,
    implicit val writer: Posting2CommentRelationshipWriter,
    implicit val reader: Posting2CommentRelationshipReader) extends Posting2CommentRelationshipDAO {
  
 val collection = colFactory.getCollection(Posting2CommentRelationshipCollectionType)

}