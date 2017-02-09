package com.emakarovas.animalrescue.persistence.dao.impl

import com.emakarovas.animalrescue.persistence.dao.PostingDAO
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.mongo.PostingModelCollectionType
import com.emakarovas.animalrescue.persistence.reader.PostingReader
import com.emakarovas.animalrescue.persistence.writer.PostingWriter

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultPostingDAO @Inject() (
    colFactory: MongoCollectionFactory,
    implicit val writer: PostingWriter,
    implicit val reader: PostingReader) extends PostingDAO {
  
 val collection = colFactory.getCollection(PostingModelCollectionType)
  
}