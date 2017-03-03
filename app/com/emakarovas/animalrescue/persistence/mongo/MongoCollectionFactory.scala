package com.emakarovas.animalrescue.persistence.mongo

import scala.concurrent.Future

import reactivemongo.api.collections.bson.BSONCollection
import com.google.inject.ImplementedBy
import com.emakarovas.animalrescue.persistence.mongo.impl.DefaultMongoCollectionFactory

@ImplementedBy(classOf[DefaultMongoCollectionFactory])
trait MongoCollectionFactory {

  def getCollection(t: MongoCollectionType[_]): Future[BSONCollection]
  
}