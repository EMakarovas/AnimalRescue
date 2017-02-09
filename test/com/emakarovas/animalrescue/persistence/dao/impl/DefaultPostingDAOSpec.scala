package com.emakarovas.animalrescue.persistence.dao.impl

import scala.util.Failure
import scala.util.Success

import org.scalatestplus.play.OneAppPerSuite

import com.emakarovas.animalrescue.model.PostingModel
import com.emakarovas.animalrescue.testutil.DelayedPlaySpec

import play.api.test.Helpers.await
import play.api.test.Helpers.defaultAwaitTimeout
import java.util.Calendar
import java.util.Date

class DefaultPostingDAOSpec extends DelayedPlaySpec with OneAppPerSuite {
  
  val Posting1Id = "posting id"
  val Posting1Date = new Date
  val Posting1Text = "posting text"
  val Posting2Id = "posting 2 id"
  val Posting2Date = buildDate(5, 5, 2015)
  val Posting2Text = "posting 2 text"
  val Posting1TextUpdated = "posting 2 text updated"
  
  val posting1 = new PostingModel(Posting1Id, Posting1Date, Posting1Text)
  val posting2 = new PostingModel(Posting2Id, Posting2Date, Posting2Text)
  val updatedPosting = new PostingModel(Posting1Id, Posting1Date, Posting1TextUpdated)
  lazy val defaultPostingDAO: DefaultPostingDAO = app.injector.instanceOf[DefaultPostingDAO]
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  "DefaultPostingDAO" should {
    
    "store a new PostingModel in the DB when create is called" in {
      val f = defaultPostingDAO.create(posting1)
      await(f)
      f onComplete {
        case Success(n) => n mustBe 1
        case Failure(t) => fail("failed to create new PostingModel " + t)
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
          val listFuture = defaultPostingDAO.findAll()
          await(listFuture)
          listFuture onComplete {
            case Success(list) => list.contains(posting1) mustBe true; list.contains(posting2) mustBe true
            case Failure(t) => fail("failed to retrieve list of PostingModels " + t)
          }
        }
        case Failure(t) => fail("failed to save second PostingModel in DB " + t)
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
  
  def buildDate(day: Int, month: Int, year: Int): Date = {
    val cal = Calendar.getInstance
    cal.set(Calendar.DAY_OF_MONTH, day);
    cal.set(Calendar.MONTH, month);
    cal.set(Calendar.YEAR, year);
    cal.getTime
  }

}