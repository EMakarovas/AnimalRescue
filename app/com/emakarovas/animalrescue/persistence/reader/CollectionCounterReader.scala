package com.emakarovas.animalrescue.persistence.reader

import com.emakarovas.animalrescue.model.AbstractModel
import com.emakarovas.animalrescue.model.CollectionCounterEntity
import com.emakarovas.animalrescue.model.constants.CollectionCounterConstants
import com.emakarovas.animalrescue.model.enumeration.ModelType
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants
import com.emakarovas.animalrescue.persistence.reader.enumeration.ModelTypeReader

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class CollectionCounterReader @Inject() (
    implicit val modelTypeReader: ModelTypeReader) extends AbstractEntityReader[CollectionCounterEntity] {
  override def read(doc: BSONDocument): CollectionCounterEntity = {
    val modelType = doc.getAs[ModelType[AbstractModel]](MongoConstants.MongoId).get
    val count = doc.getAs[Int](CollectionCounterConstants.Count).get
    CollectionCounterEntity(modelType, count)
  }
}