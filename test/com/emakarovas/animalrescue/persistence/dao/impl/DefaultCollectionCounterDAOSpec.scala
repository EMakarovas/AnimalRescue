package com.emakarovas.animalrescue.persistence.dao.impl

import scala.concurrent.Future

import org.scalatestplus.play.OneAppPerSuite

import com.emakarovas.animalrescue.model.enumeration.ModelType
import com.emakarovas.animalrescue.persistence.mongo.CollectionCounter
import com.emakarovas.animalrescue.persistence.mongo.impl.DefaultMongoCollectionFactory
import com.emakarovas.animalrescue.persistence.reader.CollectionCounterReader
import com.emakarovas.animalrescue.persistence.writer.CollectionCounterWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.ModelTypeWriter
import com.emakarovas.animalrescue.testutil.DelayedPlaySpec

import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.BSONDocument
import reactivemongo.api.commands.WriteResult

import play.api.test.Helpers.await
import play.api.test.Helpers.defaultAwaitTimeout

class DefaultCollectionCounterDAOSpec extends DelayedPlaySpec with OneAppPerSuite {
  
  private lazy val mongoCollectionFactory = app.injector.instanceOf[DefaultMongoCollectionFactory]
  private lazy val collectionCounterReader = app.injector.instanceOf[CollectionCounterReader]
  private lazy val collectionCounterWriter = app.injector.instanceOf[CollectionCounterWriter]
  private implicit lazy val modelTypeWriter = app.injector.instanceOf[ModelTypeWriter]
  private val defaultCollectionCounterDAO: DefaultCollectionCounterDAO = createDefaultCollectionCounterDAO
    
  import scala.concurrent.ExecutionContext.Implicits.global
 
  it should {
    
    "create collection counters if they don't exist during initialization" in {
      for(modelType <- ModelType.values) {
        defaultCollectionCounterDAO.get(modelType) mustBe 1            
      }
    }
    
    "return the correct id when getAndIncrement is called" in {
      delay()
      for(modelType <- ModelType.values; i <- 2 to 10) {
        val id = defaultCollectionCounterDAO.incrementAndGet(modelType)
        id mustBe i
      }
      delay(1000) // make sure the incrementing has completed in Mongo
    }
    
    "initialize a new DefaultCollectionCounterDAO with the correct values after incrementing" in {
      val newDAO = createDefaultCollectionCounterDAO
      for(modelType <- ModelType.values) {
        newDAO.get(modelType) mustBe 10
      }
      val deleteF = clearCollection(defaultCollectionCounterDAO.collection)
      await(deleteF)
    }
    
  }
  
  private def createDefaultCollectionCounterDAO: DefaultCollectionCounterDAO = new DefaultCollectionCounterDAO(
      mongoCollectionFactory, collectionCounterReader, collectionCounterWriter, modelTypeWriter)
  
  private def clearCollection(coll: Future[BSONCollection])(implicit modelTypeWriter: ModelTypeWriter): Future[Unit] = {
    val futureList = scala.collection.mutable.ListBuffer.empty[Future[WriteResult]]
    for(modelType <- ModelType.values) {
      val selector = BSONDocument("_id" -> modelTypeWriter.write(modelType))
      val f = coll.flatMap(_.remove(selector))
      futureList += f
    } 
    Future.sequence(futureList).map(_ => Unit)
  }
  
}