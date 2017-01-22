package com.emakarovas.animalrescue.persistence.dao.impl

import com.emakarovas.animalrescue.persistence.dao.CommentDAO
import com.emakarovas.animalrescue.persistence.mongo.CommentModelCollectionType
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.reader.CommentReader
import com.emakarovas.animalrescue.persistence.writer.CommentWriter

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultCommentDAO @Inject() (
    colFactory: MongoCollectionFactory,
    implicit val writer: CommentWriter,
    implicit val reader: CommentReader) extends CommentDAO {
  
 val collection = colFactory.getCollection(CommentModelCollectionType)
  
}