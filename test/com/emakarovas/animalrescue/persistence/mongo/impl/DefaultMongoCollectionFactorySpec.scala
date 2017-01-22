package com.emakarovas.animalrescue.persistence.mongo.impl

import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.OneAppPerSuite
import com.emakarovas.animalrescue.persistence.mongo.PersonModelCollectionType
import scala.util.Success
import scala.util.Failure
import com.emakarovas.animalrescue.persistence.mongo.AnimalModelCollectionType
import com.emakarovas.animalrescue.persistence.mongo.UserModelCollectionType
import com.emakarovas.animalrescue.persistence.mongo.User2PersonRelationshipCollectionType
import com.emakarovas.animalrescue.persistence.mongo.PostingModelCollectionType
import com.emakarovas.animalrescue.persistence.mongo.GeolocationModelCollectionType
import com.emakarovas.animalrescue.persistence.mongo.CommentModelCollectionType
import com.emakarovas.animalrescue.persistence.mongo.CostModelCollectionType
import com.emakarovas.animalrescue.persistence.mongo.Posting2AnimalRelationshipCollectionType
import com.emakarovas.animalrescue.persistence.mongo.Posting2GeolocationRelationshipCollectionType
import com.emakarovas.animalrescue.persistence.mongo.Posting2CostRelationshipCollectionType
import com.emakarovas.animalrescue.persistence.mongo.Posting2CommentRelationshipCollectionType
import com.emakarovas.animalrescue.persistence.mongo.User2PostingRelationshipCollectionType

class DefaultMongoCollectionFactorySpec extends PlaySpec with OneAppPerSuite {
  
  lazy val defaultMongoCollectionFactory: DefaultMongoCollectionFactory = app.injector.instanceOf[DefaultMongoCollectionFactory]
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  "DefaultMongoCollectionFactory" should {
    
    "return animal collection when getCollection(AnimalModelCollectionType) is called" in {
      val animalColFuture = defaultMongoCollectionFactory.getCollection(AnimalModelCollectionType)
      animalColFuture onComplete {
        case Success(col) => col.name mustBe app.configuration.underlying.getString("mongo.collectionNames.animalModel")
        case Failure(t) => fail("animalColFuture failed " + t)
      }
    }
    
    "return comment collection when getCollection(CommentModelCollectionType) is called" in {
      val commentColFuture = defaultMongoCollectionFactory.getCollection(CommentModelCollectionType)
      commentColFuture onComplete {
        case Success(col) => col.name mustBe app.configuration.underlying.getString("mongo.collectionNames.commentModel")
        case Failure(t) => fail("commentColFuture failed " + t)
      }
    }
    
    "return cost collection when getCollection(CostModelCollectionType) is called" in {
      val costColFuture = defaultMongoCollectionFactory.getCollection(CostModelCollectionType)
      costColFuture onComplete {
        case Success(col) => col.name mustBe app.configuration.underlying.getString("mongo.collectionNames.costModel")
        case Failure(t) => fail("costColFuture failed " + t)
      }
    }
    
    "return geolocation collection when getCollection(GeolocationModelCollectionType) is called" in {
      val geolocationColFuture = defaultMongoCollectionFactory.getCollection(GeolocationModelCollectionType)
      geolocationColFuture onComplete {
        case Success(col) => col.name mustBe app.configuration.underlying.getString("mongo.collectionNames.geolocationModel")
        case Failure(t) => fail("geolocationColFuture failed " + t)
      }
    }
    
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
    
    "return user collection when getCollection(UserModelCollectionType) is called" in {
      val userColFuture = defaultMongoCollectionFactory.getCollection(UserModelCollectionType)
      userColFuture onComplete {
        case Success(col) => col.name mustBe app.configuration.underlying.getString("mongo.collectionNames.userModel")
        case Failure(t) => fail("userColFuture failed " + t)
      }
    }
    
    "return posting2AnimalRel collection when getCollection(Posting2AnimalRelationshipCollectionType) is called" in {
      val f = defaultMongoCollectionFactory.getCollection(Posting2AnimalRelationshipCollectionType)
      f onComplete {
        case Success(col) => col.name mustBe app.configuration.underlying.getString("mongo.collectionNames.posting2AnimalRelationship")
        case Failure(t) => fail("user2personRel failed " + t)
      }
    }
    
    "return posting2CommentRel collection when getCollection(Posting2CommentRelationshipCollectionType) is called" in {
      val f = defaultMongoCollectionFactory.getCollection(Posting2CommentRelationshipCollectionType)
      f onComplete {
        case Success(col) => col.name mustBe app.configuration.underlying.getString("mongo.collectionNames.posting2CommentRelationship")
        case Failure(t) => fail("user2personRel failed " + t)
      }
    }
    
    "return posting2CostRel collection when getCollection(Posting2CostRelationshipCollectionType) is called" in {
      val f = defaultMongoCollectionFactory.getCollection(Posting2CostRelationshipCollectionType)
      f onComplete {
        case Success(col) => col.name mustBe app.configuration.underlying.getString("mongo.collectionNames.posting2CostRelationship")
        case Failure(t) => fail("user2personRel failed " + t)
      }
    }
    
    "return posting2GeolocationRel collection when getCollection(Posting2GeolocationRelationshipCollectionType) is called" in {
      val f = defaultMongoCollectionFactory.getCollection(Posting2GeolocationRelationshipCollectionType)
      f onComplete {
        case Success(col) => col.name mustBe app.configuration.underlying.getString("mongo.collectionNames.posting2GeolocationRelationship")
        case Failure(t) => fail("user2personRel failed " + t)
      }
    }
    
    "return user2PersonRel collection when getCollection(User2PersonRelationshipCollectionType) is called" in {
      val f = defaultMongoCollectionFactory.getCollection(User2PersonRelationshipCollectionType)
      f onComplete {
        case Success(col) => col.name mustBe app.configuration.underlying.getString("mongo.collectionNames.user2PersonRelationship")
        case Failure(t) => fail("user2personRel failed " + t)
      }
    }
    
    "return user2PostingRel collection when getCollection(User2PostingRelationshipCollectionType) is called" in {
      val f = defaultMongoCollectionFactory.getCollection(User2PostingRelationshipCollectionType)
      f onComplete {
        case Success(col) => col.name mustBe app.configuration.underlying.getString("mongo.collectionNames.user2PostingRelationship")
        case Failure(t) => fail("user2personRel failed " + t)
      }
    }
    
  }
  
}