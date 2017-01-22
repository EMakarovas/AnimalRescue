package com.emakarovas.animalrescue.persistence.mongo.impl

import scala.concurrent.Future

import com.emakarovas.animalrescue.persistence.mongo.AnimalModelCollectionType
import com.emakarovas.animalrescue.persistence.mongo.CommentModelCollectionType
import com.emakarovas.animalrescue.persistence.mongo.CostModelCollectionType
import com.emakarovas.animalrescue.persistence.mongo.GeolocationModelCollectionType
import com.emakarovas.animalrescue.persistence.mongo.Mongo
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionType
import com.emakarovas.animalrescue.persistence.mongo.PersonModelCollectionType
import com.emakarovas.animalrescue.persistence.mongo.Posting2AnimalRelationshipCollectionType
import com.emakarovas.animalrescue.persistence.mongo.Posting2CommentRelationshipCollectionType
import com.emakarovas.animalrescue.persistence.mongo.Posting2CostRelationshipCollectionType
import com.emakarovas.animalrescue.persistence.mongo.Posting2GeolocationRelationshipCollectionType
import com.emakarovas.animalrescue.persistence.mongo.PostingModelCollectionType
import com.emakarovas.animalrescue.persistence.mongo.User2PersonRelationshipCollectionType
import com.emakarovas.animalrescue.persistence.mongo.User2PostingRelationshipCollectionType
import com.emakarovas.animalrescue.persistence.mongo.UserModelCollectionType

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.api.collections.bson.BSONCollection

@Singleton
class DefaultMongoCollectionFactory @Inject() (
    mongo: Mongo,
    configuration: play.api.Configuration) extends MongoCollectionFactory {
  
  private val AnimalColName = getColName("animalModel")
  private val CommentColName = getColName("commentModel")
  private val CostColName = getColName("costModel")
  private val GeolocationColName = getColName("geolocationModel")
  private val PersonColName = getColName("personModel")
  private val PostingColName = getColName("postingModel")
  private val UserColName = getColName("userModel")
  
  private val Posting2AnimalRelColName = getColName("posting2AnimalRelationship")
  private val Posting2CommentRelColName = getColName("posting2CommentRelationship")
  private val Posting2CostRelColName = getColName("posting2CostRelationship")
  private val Posting2GeolocationRelColName = getColName("posting2GeolocationRelationship")
  private val User2PersonRelColName = getColName("user2PersonRelationship")
  private val User2PostingRelColName = getColName("user2PostingRelationship")

  import scala.concurrent.ExecutionContext.Implicits.global
  
  def getCollection(t: MongoCollectionType): Future[BSONCollection] = {
    t match {
      case AnimalModelCollectionType => getCollection(AnimalColName)
      case CommentModelCollectionType => getCollection(CommentColName)
      case CostModelCollectionType => getCollection(CostColName)
      case GeolocationModelCollectionType => getCollection(GeolocationColName)
      case PersonModelCollectionType => getCollection(PersonColName)
      case PostingModelCollectionType => getCollection(PostingColName)
      case UserModelCollectionType => getCollection(UserColName)
      case Posting2AnimalRelationshipCollectionType => getCollection(Posting2AnimalRelColName)
      case Posting2CommentRelationshipCollectionType => getCollection(Posting2CommentRelColName)
      case Posting2CostRelationshipCollectionType => getCollection(Posting2CostRelColName)
      case Posting2GeolocationRelationshipCollectionType => getCollection(Posting2GeolocationRelColName)
      case User2PersonRelationshipCollectionType => getCollection(User2PersonRelColName)
      case User2PostingRelationshipCollectionType => getCollection(User2PostingRelColName)
    }
  }

  private def getCollection(colName: String): Future[BSONCollection] = mongo.db.map(_.collection(colName))
  
  private def getColName(name: String) = configuration.underlying.getString("mongo.collectionNames." + name)
  
}