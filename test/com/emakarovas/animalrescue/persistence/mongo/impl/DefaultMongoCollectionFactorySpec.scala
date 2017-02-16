package com.emakarovas.animalrescue.persistence.mongo.impl

import scala.util.Failure
import scala.util.Success

import org.scalatestplus.play.OneAppPerSuite
import org.scalatestplus.play.PlaySpec

import com.emakarovas.animalrescue.persistence.mongo.PersonModelCollectionType
import com.emakarovas.animalrescue.persistence.mongo.PostingAnimalModelCollectionType
import com.emakarovas.animalrescue.persistence.mongo.PostingCommentModelCollectionType
import com.emakarovas.animalrescue.persistence.mongo.PostingModelCollectionType
import com.emakarovas.animalrescue.persistence.mongo.UserModelCollectionType
import com.emakarovas.animalrescue.persistence.mongo.WishModelCollectionType

class DefaultMongoCollectionFactorySpec extends PlaySpec with OneAppPerSuite {
  
  lazy val defaultMongoCollectionFactory: DefaultMongoCollectionFactory = app.injector.instanceOf[DefaultMongoCollectionFactory]
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  "DefaultMongoCollectionFactory" should {
    
    "return person collection when getCollection(PersonModelCollectionType) is called" in {
      val personColFuture = defaultMongoCollectionFactory.getCollection(PersonModelCollectionType)
      personColFuture onComplete {
        case Success(col) => col.name mustBe app.configuration.underlying.getString("mongo.collectionNames.personModel")
        case Failure(t) => fail("personColFuture failed " + t)
      }
    }
    
    "return posting collection when getCollection(PostingModelCollectionType) is called" in {
      val postingColFuture = defaultMongoCollectionFactory.getCollection(PostingModelCollectionType)
      postingColFuture onComplete {
        case Success(col) => col.name mustBe app.configuration.underlying.getString("mongo.collectionNames.postingModel")
        case Failure(t) => fail("postingColFuture failed " + t)
      }
    }
    
        "return animal collection when getCollection(AnimalModelCollectionType) is called" in {
      val animalColFuture = defaultMongoCollectionFactory.getCollection(PostingAnimalModelCollectionType)
      animalColFuture onComplete {
        case Success(col) => col.name mustBe app.configuration.underlying.getString("mongo.collectionNames.postingAnimalModel")
        case Failure(t) => fail("animalColFuture failed " + t)
      }
    }
    
    "return comment collection when getCollection(CommentModelCollectionType) is called" in {
      val commentColFuture = defaultMongoCollectionFactory.getCollection(PostingCommentModelCollectionType)
      commentColFuture onComplete {
        case Success(col) => col.name mustBe app.configuration.underlying.getString("mongo.collectionNames.postingCommentModel")
        case Failure(t) => fail("commentColFuture failed " + t)
      }
    }
    
    "return user collection when getCollection(UserModelCollectionType) is called" in {
      val userColFuture = defaultMongoCollectionFactory.getCollection(UserModelCollectionType)
      userColFuture onComplete {
        case Success(col) => col.name mustBe app.configuration.underlying.getString("mongo.collectionNames.userModel")
        case Failure(t) => fail("userColFuture failed " + t)
      }
    }
    
    "return wish collection when getCollection(WishModelCollectionType) is called" in {
      val wishColFuture = defaultMongoCollectionFactory.getCollection(WishModelCollectionType)
      wishColFuture onComplete {
        case Success(col) => col.name mustBe app.configuration.underlying.getString("mongo.collectionNames.wishModel")
        case Failure(t) => fail("wishColFuture failed " + t)
      }
    }
    
  }
  
}