package com.emakarovas.animalrescue.persistence.writer

import com.emakarovas.animalrescue.model.CollectionCounterEntity
import com.emakarovas.animalrescue.model.constants.CollectionCounterConstants
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants
import com.emakarovas.animalrescue.persistence.writer.enumeration.ModelTypeWriter

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class CollectionCounterWriter @Inject() (
    implicit private val modelTypeWriter: ModelTypeWriter) extends AbstractEntityWriter[CollectionCounterEntity] {
  override def write(counter: CollectionCounterEntity): BSONDocument = {
    BSONDocument(
        MongoConstants.MongoId -> counter.modelType,
        CollectionCounterConstants.Count -> counter.count)
  }
}