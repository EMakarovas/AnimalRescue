package com.emakarovas.animalrescue.persistence.mongo.impl

import scala.concurrent.Future

import com.emakarovas.animalrescue.persistence.mongo.Mongo
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionType

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.api.collections.bson.BSONCollection

@Singleton
class DefaultMongoCollectionFactory @Inject() (
    mongo: Mongo,
    configuration: play.api.Configuration) extends MongoCollectionFactory {
  
  private val AnimalColName = getColName("animalModel")
  private val CollectionCounterColName = getColName("collectionCounterEntity")
  private val OfferColName = getColName("offerModel")
  private val SearchColName = getColName("searchModel")
  private val UserColName = getColName("userModel")

  import scala.concurrent.ExecutionContext.Implicits.global
  
  def getCollection(t: MongoCollectionType[_]): Future[BSONCollection] = {
    t match {
      case MongoCollectionType.Animal => getCollection(AnimalColName)
      case MongoCollectionType.CollectionCounter => getCollection(CollectionCounterColName)
      case MongoCollectionType.Offer => getCollection(OfferColName)
      case MongoCollectionType.Search => getCollection(SearchColName)
      case MongoCollectionType.User => getCollection(UserColName)
    }
  }

  private def getCollection(colName: String): Future[BSONCollection] = mongo.db.map(_.collection(colName))
  
  private def getColName(name: String) = configuration.underlying.getString("mongo.collectionNames." + name)
  
}