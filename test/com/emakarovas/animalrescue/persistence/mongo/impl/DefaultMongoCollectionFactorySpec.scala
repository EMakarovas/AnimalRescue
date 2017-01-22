package com.emakarovas.animalrescue.persistence.mongo.impl

import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.OneAppPerSuite
import com.emakarovas.animalrescue.persistence.mongo.PersonModelCollectionType
import scala.util.Success
import scala.util.Failure
import com.emakarovas.animalrescue.persistence.mongo.AnimalModelCollectionType
import com.emakarovas.animalrescue.persistence.mongo.UserModelCollectionType
import com.emakarovas.animalrescue.persistence.mongo.User2PersonRelationshipCollectionType

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
    
    "return person collection when getCollection(PersonModelCollectionType) is called" in {
      val personColFuture = defaultMongoCollectionFactory.getCollection(PersonModelCollectionType)
      personColFuture onComplete {
        case Success(col) => col.name mustBe app.configuration.underlying.getString("mongo.collectionNames.personModel")
        case Failure(t) => fail("personColFuture failed " + t)
      }
    }
    
    "return user collection when getCollection(UserModelCollectionType) is called" in {
      val userColFuture = defaultMongoCollectionFactory.getCollection(UserModelCollectionType)
      userColFuture onComplete {
        case Success(col) => col.name mustBe app.configuration.underlying.getString("mongo.collectionNames.userModel")
        case Failure(t) => fail("userColFuture failed " + t)
      }
    }
    
    "return user2personRel collection when getCollection(User2PersonRelationshipCollectionType) is called" in {
      val f = defaultMongoCollectionFactory.getCollection(User2PersonRelationshipCollectionType)
      f onComplete {
        case Success(col) => col.name mustBe app.configuration.underlying.getString("mongo.collectionNames.user2PersonRelationship")
        case Failure(t) => fail("user2personRel failed " + t)
      }
    }
    
  }
  
}