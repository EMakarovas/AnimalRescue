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

class DefaultAnimalDAOSpec extends DelayedPlaySpec with OneAppPerSuite {
  
  lazy val defaultAnimalDAO = app.injector.instanceOf[DefaultAnimalDAO]
    
  import scala.concurrent.ExecutionContext.Implicits.global
 
  it should {
    
    
    
  }
  
  private val Animal1 = ???
  
//  (override val id: String,
//   override val animalType: AnimalType,
//   override val specificType: Option[String],
//   override val gender: Gender,
//   name: Option[String],
//   age: Option[Int], // stored as months
//   description: Option[String],
//   image: Option[ImageModel],
//   video: Option[VideoModel],
//   adoptionDetails: Option[AdoptionDetailsModel],
//   offerDetails: Option[OfferDetailsModel])
//   extends AbstractModel(id) with AbstractAnimalEntity with AbstractPersistableEntity
//
//case class OfferDetailsModel
//  (foodCost: Option[Double],
//   shelterCost: Option[Double],
//   vaccinationCost: Option[Double],
//   offerId: String,
//   offerTerminationReason: Option[OfferTerminationReasonModel]) extends AbstractEntity
//
//case class OfferTerminationReasonModel
//  (offerTerminationReason: OfferTerminationReason,
//   text: Option[String]) extends AbstractEntity
//
//case class AdoptionDetailsModel
//  (ownerId: Option[String], // ID of the user
//   date: Date) extends AbstractEntity

  
}