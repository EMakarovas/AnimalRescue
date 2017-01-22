package com.emakarovas.animalrescue.persistence.mongo.impl

import scala.concurrent.Future

import com.emakarovas.animalrescue.persistence.mongo.AnimalModelCollectionType
import com.emakarovas.animalrescue.persistence.mongo.Mongo
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionType
import com.emakarovas.animalrescue.persistence.mongo.PersonModelCollectionType
import com.emakarovas.animalrescue.persistence.mongo.User2PersonRelationshipCollectionType
import com.emakarovas.animalrescue.persistence.mongo.UserModelCollectionType

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.api.collections.bson.BSONCollection

@Singleton
class DefaultMongoCollectionFactory @Inject() (
    mongo: Mongo,
    configuration: play.api.Configuration) extends MongoCollectionFactory {
  
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