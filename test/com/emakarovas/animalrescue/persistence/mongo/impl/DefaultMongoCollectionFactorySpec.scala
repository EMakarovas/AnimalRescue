package com.emakarovas.animalrescue.persistence.mongo.impl

import scala.util.Failure
import scala.util.Success

import org.scalatestplus.play.OneAppPerSuite
import org.scalatestplus.play.PlaySpec

import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionType

class DefaultMongoCollectionFactorySpec extends PlaySpec with OneAppPerSuite {
  
  lazy val defaultMongoCollectionFactory: DefaultMongoCollectionFactory = app.injector.instanceOf[DefaultMongoCollectionFactory]
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  "DefaultMongoCollectionFactory" should {
    
    "return animal collection when getCollection(Animal) is called" in {
      val animalColFuture = defaultMongoCollectionFactory.getCollection(MongoCollectionType.Animal)
      animalColFuture onComplete {
        case Success(col) => col.name mustBe app.configuration.underlying.getString("mongo.collectionNames.animalModel")
        case Failure(t) => fail("animalColFuture failed " + t)
      }
    }
    
    "return offer collection when getCollection(Offer) is called" in {
      val offerColFuture = defaultMongoCollectionFactory.getCollection(MongoCollectionType.Offer)
      offerColFuture onComplete {
        case Success(col) => col.name mustBe app.configuration.underlying.getString("mongo.collectionNames.offerModel")
        case Failure(t) => fail("offerColFuture failed " + t)
      }
    }
    
    "return search collection when getCollection(Search) is called" in {
      val searchColFuture = defaultMongoCollectionFactory.getCollection(MongoCollectionType.Search)
      searchColFuture onComplete {
        case Success(col) => col.name mustBe app.configuration.underlying.getString("mongo.collectionNames.searchModel")
        case Failure(t) => fail("searchColFuture failed " + t)
      }
    }
    
    "return user collection when getCollection(User) is called" in {
      val userColFuture = defaultMongoCollectionFactory.getCollection(MongoCollectionType.User)
      userColFuture onComplete {
        case Success(col) => col.name mustBe app.configuration.underlying.getString("mongo.collectionNames.userModel")
        case Failure(t) => fail("userColFuture failed " + t)
      }
    }
    
    "return collectionCounter collection when getCollection(CollectionCounter) is called" in {
      val collectionCounterColFuture = defaultMongoCollectionFactory.getCollection(MongoCollectionType.CollectionCounter)
      collectionCounterColFuture onComplete {
        case Success(col) => col.name mustBe app.configuration.underlying.getString("mongo.collectionNames.collectionCounterEntity")
        case Failure(t) => fail("collectionCounterColFuture failed " + t)
      }
    }
    
  }
  
}