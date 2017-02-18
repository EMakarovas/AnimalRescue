package com.emakarovas.animalrescue.persistence.dao.impl

import java.util.Date

import scala.util.Failure
import scala.util.Success

import org.scalatestplus.play.OneAppPerSuite

import com.emakarovas.animalrescue.model.CostModel
import com.emakarovas.animalrescue.model.GeolocationModel
import com.emakarovas.animalrescue.model.PostingModel
import com.emakarovas.animalrescue.model.enumeration.CostType
import com.emakarovas.animalrescue.testutil.DelayedPlaySpec
import com.emakarovas.animalrescue.testutil.TestUtils

import play.api.test.Helpers.await
import play.api.test.Helpers.defaultAwaitTimeout
import reactivemongo.core.errors.DatabaseException

class DefaultPostingDAOSpec extends DelayedPlaySpec with OneAppPerSuite {
  
  val Posting1Id = "posting id"
  val Posting1StartDate = new Date
  val Posting1EndDate = Some(TestUtils.buildDate(6, 6, 2011))
  val Posting1Text = "posting text"
  val Posting1CostList = List(CostModel("cost id", CostType.Food, 1))
  val Posting1Geolocation = GeolocationModel("geo id", 1, 1)
  val Posting1UserId = "user id"
  val Posting1Available = true
  val Posting2Id = "posting 2 id"
  val Posting2StartDate = TestUtils.buildDate(5, 5, 2015)
  val Posting2EndDate = None
  val Posting2Text = "posting 2 text"
  val Posting2CostList = List(CostModel("cost 2 id", CostType.Food, 2))
  val Posting2Geolocation = GeolocationModel("geo id", 2, 2)
  val Posting2UserId = "user id"
  val Posting2Available = false
  val Posting1TextUpdated = "posting 2 text updated"
  
  val posting1 = PostingModel(Posting1Id, Posting1StartDate, Posting1EndDate, Posting1Text, Posting1CostList, Posting1Geolocation, Posting1UserId, Posting1Available)
  val posting2 = PostingModel(Posting2Id, Posting2StartDate, Posting2EndDate, Posting2Text, Posting2CostList, Posting2Geolocation, Posting2UserId, Posting2Available)
  val updatedPosting = PostingModel(Posting1Id, Posting1StartDate, Posting1EndDate, Posting1TextUpdated, Posting1CostList, Posting1Geolocation, Posting1UserId, Posting1Available)
  lazy val defaultPostingDAO: DefaultPostingDAO = app.injector.instanceOf[DefaultPostingDAO]
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  "DefaultPostingDAO" should {
    
    "store a new PostingModel in the DB when create is called" in {
      val f = defaultPostingDAO.create(posting1)
      await(f)
      f onComplete {
        case Success(n) => n mustBe 1
        case Failure(t) => println("failas")
      }
      f recover {
        case e: DatabaseException => println("rec")
      }
    }
    
    "find the correct PostingModel from the DB when find is called" in {
      delay
      val retrievedPosting = defaultPostingDAO.findById(Posting1Id)
      await(retrievedPosting)
      retrievedPosting onComplete {
        case Success(option) => option.get mustBe posting1
        case Failure(t) => fail("failed to retrieve the PostingModel " + t)
      }
    }
    
    "find the list of all PostingModels when findAll is called" in {
      delay
      val saveFuture = defaultPostingDAO.create(posting2)
      await(saveFuture)
      saveFuture onComplete {
        case Success(n) => {
          delay
          val listFuture = defaultPostingDAO.findAll()
          await(listFuture)
          listFuture onComplete {
            case Success(list) => list.size mustBe 2; list.contains(posting1) mustBe true; list.contains(posting2) mustBe true
            case Failure(t) => fail("failed to retrieve list of PostingModels " + t)
          }
        }
        case Failure(t) => fail("failed to save second PostingModel in DB " + t)
      } 
    }
    
    "find the list of all available PostingModels when findAllAvailable is called" in {
      delay
      val listFuture = defaultPostingDAO.findAllAvailable()
      await(listFuture)
      listFuture onComplete {
        case Success(list) => list.size mustBe 1; list.contains(posting1) mustBe true
        case Failure(t) => fail("failed to retrieve list of PostingModels " + t)
      }
    }
    
    "find the correct PostingModel from the DB when findByUserId is called" in {
      delay
      val retrievedPosting = defaultPostingDAO.findByUserId(Posting1UserId)
      await(retrievedPosting)
      retrievedPosting onComplete {
        case Success(list) => list.size mustBe 2; list.contains(posting1) mustBe true; list.contains(posting2) mustBe true;
        case Failure(t) => fail("failed to retrieve the PostingModel " + t)
      }
    }
    
    "update a PostingModel when update is called" in {
      delay
      val updateFuture = defaultPostingDAO.update(updatedPosting)
      await(updateFuture)
      updateFuture onComplete {
        case Success(n) => {
          n mustBe 1
          val updatedPostingFuture = defaultPostingDAO.findById(Posting1Id)
          await(updatedPostingFuture)
          updatedPostingFuture onComplete {
            case Success(postingOption) => postingOption.get.text mustBe Posting1TextUpdated
            case Failure(t) => fail("failed to retrieve updated PostingModel " + t)
          }
        }
        case Failure(t) => fail("failed to update PostingModel " + t)
      }
    }
    
    "delete a PostingModel when deleteById is called" in {
      delay
      val deleteFuture = defaultPostingDAO.deleteById(Posting1Id)
      await(deleteFuture)
      deleteFuture onComplete {
        case Success(n) => {
          n mustBe 1
          val existingPostingListFuture = defaultPostingDAO.findAll()
          await(existingPostingListFuture)
          existingPostingListFuture onComplete {
            case Success(list) => {
              list.contains(posting1) mustBe false
              list.contains(posting2) mustBe true
              val deleteFuture2 = defaultPostingDAO.deleteById(Posting2Id)
              await(deleteFuture2)
              deleteFuture2 onComplete {
                case Success(n) => {
                  n mustBe 1
                  val listFuture = defaultPostingDAO.findAll()
                  await(listFuture)
                  listFuture onComplete {
                    case Success(list) => list.size mustBe 0
                    case Failure(t) => fail("failed to retrieve PostingModel list for second time " + t)
                  }
                }
                case Failure(t) => fail("failed to delete second PostingModel " + t)
              }
            }
            case Failure(t) => fail("failed to retrieve list of PostingModels when testing delete " + t)
          }
        }
        case Failure(t) => fail("failed to delete first PostingModel " + t)
      }
    }
    
  }

}