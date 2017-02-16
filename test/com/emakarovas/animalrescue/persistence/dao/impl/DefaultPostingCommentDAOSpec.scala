package com.emakarovas.animalrescue.persistence.dao.impl

import scala.util.Failure
import scala.util.Success

import org.scalatestplus.play.OneAppPerSuite

import com.emakarovas.animalrescue.model.PostingCommentModel
import com.emakarovas.animalrescue.testutil.DelayedPlaySpec
import com.emakarovas.animalrescue.testutil.TestUtils

import play.api.test.Helpers.await
import play.api.test.Helpers.defaultAwaitTimeout

class DefaultPostingCommentDAOSpec extends DelayedPlaySpec with OneAppPerSuite {
  
  val PostingComment1Id = "postingComment id"
  val PostingComment1Date = new java.util.Date()
  val PostingComment1Text = "postingComment 1 text"
  val PostingComment1PostingId = "posting id"
  val PostingComment2Id = "postingComment2 id"
  val PostingComment2Date = TestUtils.buildDate(5, 6, 2010);
  val PostingComment2Text = "postingComment 2 text"
  val PostingComment2PostingId = "posting 2 id"

  val PostingComment1DateUpdated = TestUtils.buildDate(1, 2, 2011);
  
  val postingComment1 = new PostingCommentModel(PostingComment1Id, PostingComment1Date, PostingComment1Text, PostingComment1PostingId)
  val postingComment2 = new PostingCommentModel(PostingComment2Id, PostingComment2Date, PostingComment2Text, PostingComment2PostingId)
  val updatedPostingComment = new PostingCommentModel(PostingComment1Id, PostingComment1DateUpdated, PostingComment1Text, PostingComment1PostingId)
  lazy val defaultPostingCommentDAO: DefaultPostingCommentDAO = app.injector.instanceOf[DefaultPostingCommentDAO]
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  "DefaultPostingCommentDAO" should {
    
    "store a new PostingCommentModel in the DB when create is called" in {
      val f = defaultPostingCommentDAO.create(postingComment1)
      await(f)
      f onComplete {
        case Success(n) => n mustBe 1
        case Failure(t) => fail("failed to create new PostingCommentModel " + t)
      }
    }
    
    "find the correct PostingCommentModel from the DB when find is called" in {
      delay
      val retrievedPostingComment = defaultPostingCommentDAO.findById(PostingComment1Id)
      await(retrievedPostingComment)
      retrievedPostingComment onComplete {
        case Success(option) => option.get mustBe postingComment1
        case Failure(t) => fail("failed to retrieve the PostingCommentModel " + t)
      }
    }
    
    "find the list of all PostingCommentModels when findAll is called" in {
      delay
      val saveFuture = defaultPostingCommentDAO.create(postingComment2)
      await(saveFuture)
      saveFuture onComplete {
        case Success(n) => {
          val listFuture = defaultPostingCommentDAO.findAll()
          await(listFuture)
          listFuture onComplete {
            case Success(list) => list.contains(postingComment1) mustBe true; list.contains(postingComment2) mustBe true
            case Failure(t) => fail("failed to retrieve list of PostingCommentModels " + t)
          }
        }
        case Failure(t) => fail("failed to save second PostingCommentModel in DB " + t)
      } 
    }
    
    "find the correct PostingCommentModel from the DB when findByPostingId is called" in {
      delay
      val retrievedPostingComment = defaultPostingCommentDAO.findByPostingId(PostingComment1PostingId)
      await(retrievedPostingComment)
      retrievedPostingComment onComplete {
        case Success(list) => list.size mustBe 1; list.contains(postingComment1) mustBe true
        case Failure(t) => fail("failed to retrieve the PostingCommentModel " + t)
      }
    }
    
    "update a PostingCommentModel when update is called" in {
      delay
      val updateFuture = defaultPostingCommentDAO.update(updatedPostingComment)
      await(updateFuture)
      updateFuture onComplete {
        case Success(n) => {
          n mustBe 1
          val updatedPostingCommentFuture = defaultPostingCommentDAO.findById(PostingComment1Id)
          await(updatedPostingCommentFuture)
          updatedPostingCommentFuture onComplete {
            case Success(postingCommentOption) => postingCommentOption.get.date mustBe PostingComment1DateUpdated
            case Failure(t) => fail("failed to retrieve updated PostingCommentModel " + t)
          }
        }
        case Failure(t) => fail("failed to update PostingCommentModel " + t)
      }
    }
    
    "delete a PostingCommentModel when deleteById is called" in {
      delay
      val deleteFuture = defaultPostingCommentDAO.deleteById(PostingComment1Id)
      await(deleteFuture)
      deleteFuture onComplete {
        case Success(n) => {
          n mustBe 1
          val existingPostingCommentListFuture = defaultPostingCommentDAO.findAll()
          await(existingPostingCommentListFuture)
          existingPostingCommentListFuture onComplete {
            case Success(list) => {
              list.contains(postingComment1) mustBe false
              list.contains(postingComment2) mustBe true
              val deleteFuture2 = defaultPostingCommentDAO.deleteById(PostingComment2Id)
              await(deleteFuture2)
              deleteFuture2 onComplete {
                case Success(n) => {
                  n mustBe 1
                  val listFuture = defaultPostingCommentDAO.findAll()
                  await(listFuture)
                  listFuture onComplete {
                    case Success(list) => list.size mustBe 0
                    case Failure(t) => fail("failed to retrieve PostingCommentModel list for second time " + t)
                  }
                }
                case Failure(t) => fail("failed to delete second PostingCommentModel " + t)
              }
            }
            case Failure(t) => fail("failed to retrieve list of PostingCommentModels when testing delete " + t)
          }
        }
        case Failure(t) => fail("failed to delete first PostingCommentModel " + t)
      }
    }
    
  }

}