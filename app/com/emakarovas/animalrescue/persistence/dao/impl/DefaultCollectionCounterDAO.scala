package com.emakarovas.animalrescue.persistence.dao.impl

import scala.concurrent.Future
import scala.util.Failure
import scala.util.Success

import com.emakarovas.animalrescue.model.AbstractModel
import com.emakarovas.animalrescue.model.CollectionCounterEntity
import com.emakarovas.animalrescue.model.constants.CollectionCounterConstants
import com.emakarovas.animalrescue.model.enumeration.ModelType
import com.emakarovas.animalrescue.persistence.dao.CollectionCounterDAO
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants
import com.emakarovas.animalrescue.persistence.mongo.CollectionCounter
import com.emakarovas.animalrescue.persistence.mongo.MongoCollectionFactory
import com.emakarovas.animalrescue.persistence.reader.CollectionCounterReader
import com.emakarovas.animalrescue.persistence.writer.CollectionCounterWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.ModelTypeWriter

import javax.inject.Inject
import javax.inject.Singleton
import play.api.Logger
import reactivemongo.bson.BSONDocument

@Singleton
class DefaultCollectionCounterDAO @Inject() (
    val colFactory: MongoCollectionFactory,
    implicit override val reader: CollectionCounterReader,
    implicit override val writer: CollectionCounterWriter,
    implicit val modelTypeWriter: ModelTypeWriter) extends CollectionCounterDAO {
  
  val collection = colFactory.getCollection(CollectionCounter)
 
  import scala.concurrent.ExecutionContext.Implicits.global
  
  private val counterMap = scala.collection.mutable.Map[ModelType[_], Int]()
  
  // Constructor
  private var initialized = false;
  private val modelTypeLength = ModelType.values.length
  private var modelsLoaded = 0
  for(modelType <- ModelType.values) {
    val f = getCollectionCounter(modelType)
    f onComplete {
      case Success(count) => counterMap.put(modelType, count)
      case Failure(t) => {
        counterMap.put(modelType, 1)
        val initF = initializeCollectionCounter(modelType)
        initF onSuccess {
          case _ => {
            modelsLoaded += 1
            if(modelsLoaded==modelTypeLength)
              initialized = true
          }
        }
      }
    }
  }
  
  override def get(modelType: ModelType[AbstractModel]): Int = {
    waitUntilInitialized(2000)
    val count = counterMap get modelType
    count.get
  }
  
  override def incrementAndGet(modelType: ModelType[AbstractModel]): Int = {
    waitUntilInitialized(2000)
    val count = counterMap get modelType
    val incValue = count.get + 1
    incrementInDatabase(modelType)
    counterMap.put(modelType, incValue)
    incValue 
  }
  
  private def incrementInDatabase(modelType: ModelType[AbstractModel]): Unit = {
    global execute {
      new Runnable {
        var counter = 0
        override def run = {
          val selector = BSONDocument(MongoConstants.MongoId -> modelTypeWriter.write(modelType))
          val modifier = BSONDocument(
              "$inc" -> BSONDocument(CollectionCounterConstants.Count -> 1))
          collection.map(_.findAndUpdate(selector, modifier)
              .onFailure({case _ => {
                counter += 1
                if(counter<10) run
                else Logger.error(s"Failed to increase counter for $modelType")
              }
          }))
        }
      }
    } 
  }
  
  // TODO needs changing
  private def getCollectionCounter(modelType: ModelType[AbstractModel]): Future[Int] = {
    val selector = BSONDocument(MongoConstants.MongoId -> modelType)
    collection.flatMap(_.find(selector).one.map((op) => op.get.count))
  }
  
  private def initializeCollectionCounter(modelType: ModelType[AbstractModel]): Future[Unit] = {
    val colCounter = CollectionCounterEntity(modelType, 1)
    collection.flatMap(_.insert(colCounter)).map((writeRes) => {})
  }
  
  private def waitUntilInitialized(timeToWait: Int) {
    if(timeToWait<=0 && !initialized) {
      initialized = true
      Logger.warn("DefaultCollectionCounterDAO initialized by force")
    }
    if(!initialized) {
      val wait = 50
      Thread.sleep(wait)
      waitUntilInitialized(timeToWait - wait)
    }
    
  }
  
}