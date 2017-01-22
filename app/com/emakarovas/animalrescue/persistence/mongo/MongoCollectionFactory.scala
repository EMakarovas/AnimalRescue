package com.emakarovas.animalrescue.persistence.mongo

import scala.concurrent.Future

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.api.Collection
import reactivemongo.api.collections.bson.BSONCollection

trait MongoCollectionFactory {

  def getCollection(t: MongoCollectionType): Future[BSONCollection]
  
}