package com.emakarovas.animalrescue.persistence.mongo.impl

import scala.concurrent.Future

import com.emakarovas.animalrescue.persistence.mongo.Mongo
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionType
import com.emakarovas.animalrescue.persistence.mongo.PersonModelCollectionType
import com.emakarovas.animalrescue.persistence.mongo.PostingAnimalModelCollectionType
import com.emakarovas.animalrescue.persistence.mongo.PostingCommentModelCollectionType
import com.emakarovas.animalrescue.persistence.mongo.PostingModelCollectionType
import com.emakarovas.animalrescue.persistence.mongo.UserModelCollectionType

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.api.collections.bson.BSONCollection
import com.emakarovas.animalrescue.persistence.mongo.WishModelCollectionType

@Singleton
class DefaultMongoCollectionFactory @Inject() (
    mongo: Mongo,
    configuration: play.api.Configuration) extends MongoCollectionFactory {
  
  private val PersonColName = getColName("personModel")
  private val PostingColName = getColName("postingModel")
  private val PostingAnimalColName = getColName("animalModel")
  private val PostingCommentColName = getColName("commentModel")
  private val UserColName = getColName("userModel")
  private val WishColName = getColName("wishModel")

  import scala.concurrent.ExecutionContext.Implicits.global
  
  def getCollection(t: MongoCollectionType): Future[BSONCollection] = {
    t match {
      case PostingAnimalModelCollectionType => getCollection(PostingAnimalColName)
      case PostingCommentModelCollectionType => getCollection(PostingCommentColName)
      case PersonModelCollectionType => getCollection(PersonColName)
      case PostingModelCollectionType => getCollection(PostingColName)
      case UserModelCollectionType => getCollection(UserColName)
      case WishModelCollectionType => getCollection(WishColName)
    }
  }

  private def getCollection(colName: String): Future[BSONCollection] = mongo.db.map(_.collection(colName))
  
  private def getColName(name: String) = configuration.underlying.getString("mongo.collectionNames." + name)
  
}