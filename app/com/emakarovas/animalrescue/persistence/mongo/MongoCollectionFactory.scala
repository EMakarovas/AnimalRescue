package com.emakarovas.animalrescue.persistence.mongo

import scala.concurrent.Future

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.api.Collection
import reactivemongo.api.collections.bson.BSONCollection

@Singleton
class MongoCollectionFactory @Inject() (
    mongo: Mongo,
    configuration: play.api.Configuration) {
  
  private val AnimalColName = getColName("animalModel")
  private val PersonColName = getColName("personModel")
  private val UserColName = getColName("userModel")
  private val User2PersonRelColName = getColName("user2PersonRelationship")
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  def getCollection(t: MongoCollectionType): Future[BSONCollection] = {
    t match {
      case AnimalModelCollectionType => getCollection(AnimalColName)
      case PersonModelCollectionType => getCollection(PersonColName)
      case UserModelCollectionType => getCollection(UserColName)
      case User2PersonRelationshipCollectionType => getCollection(User2PersonRelColName)
    }
  }

  private def getCollection(colName: String): Future[BSONCollection] = mongo.db.map(_.collection(colName))
  
  private def getColName(name: String) = configuration.underlying.getString("mongo.collectionNames." + name)
  
}