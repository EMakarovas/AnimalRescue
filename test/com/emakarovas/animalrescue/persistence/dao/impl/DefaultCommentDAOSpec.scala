package com.emakarovas.animalrescue.persistence.dao.impl

import scala.util.Failure
import scala.util.Success

import org.scalatestplus.play.OneAppPerSuite

import com.emakarovas.animalrescue.model.CommentModel
import com.emakarovas.animalrescue.testutil.DelayedPlaySpec

import play.api.test.Helpers.await
import play.api.test.Helpers.defaultAwaitTimeout
import net.sourceforge.htmlunit.corejs.javascript.Token.CommentType

class DefaultCommentDAOSpec extends DelayedPlaySpec with OneAppPerSuite {
  
  val Comment1Id = "comment id"
  val Comment1Date = new java.util.Date()
  val Comment1Text = "comment 1 text"
  val Comment2Id = "comment2 id"
  val Comment2Date = new java.util.Date()
  Comment2Date.setHours(0)
  Comment2Date.setMinutes(0)
  val Comment2Text = "comment 2 text"

  val Comment1DateUpdated = new java.util.Date()
  Comment1DateUpdated.setHours(5)
  Comment1DateUpdated.setMinutes(5)
  
  val comment1 = new CommentModel(Comment1Id, Comment1Date, Comment1Text)
  val comment2 = new CommentModel(Comment2Id, Comment2Date, Comment2Text)
  val updatedComment = new CommentModel(Comment1Id, Comment1DateUpdated, Comment1Text)
  lazy val defaultCommentDAO: DefaultCommentDAO = app.injector.instanceOf[DefaultCommentDAO]
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  "DefaultCommentDAO" should {
    
    "store a new CommentModel in the DB when create is called" in {
      val f = defaultCommentDAO.create(comment1)
      await(f)
      f onComplete {
        case Success(n) => n mustBe 1
        case Failure(t) => fail("failed to create new CommentModel " + t)
      }
    }
    
    "find the correct CommentModel from the DB when find is called" in {
      delay
      val retrievedComment = defaultCommentDAO.findById(Comment1Id)
      await(retrievedComment)
      retrievedComment onComplete {
        case Success(option) => option.get mustBe comment1
        case Failure(t) => fail("failed to retrieve the CommentModel " + t)
      }
    }
    
    "find the list of all CommentModels when findAll is called" in {
      delay
      val saveFuture = defaultCommentDAO.create(comment2)
      await(saveFuture)
      saveFuture onComplete {
        case Success(n) => {
          val listFuture = defaultCommentDAO.findAll()
          await(listFuture)
          listFuture onComplete {
            case Success(list) => list.contains(comment1) mustBe true; list.contains(comment2) mustBe true
            case Failure(t) => fail("failed to retrieve list of CommentModels " + t)
          }
        }
        case Failure(t) => fail("failed to save second CommentModel in DB " + t)
      } 
    }
    
    "update a CommentModel when update is called" in {
      delay
      val updateFuture = defaultCommentDAO.update(updatedComment)
      await(updateFuture)
      updateFuture onComplete {
        case Success(n) => {
          n mustBe 1
          val updatedCommentFuture = defaultCommentDAO.findById(Comment1Id)
          await(updatedCommentFuture)
          updatedCommentFuture onComplete {
            case Success(commentOption) => commentOption.get.date mustBe Comment1DateUpdated
            case Failure(t) => fail("failed to retrieve updated CommentModel " + t)
          }
        }
        case Failure(t) => fail("failed to update CommentModel " + t)
      }
    }
    
    "delete a CommentModel when deleteById is called" in {
      delay
      val deleteFuture = defaultCommentDAO.deleteById(Comment1Id)
      await(deleteFuture)
      deleteFuture onComplete {
        case Success(n) => {
          n mustBe 1
          val existingCommentListFuture = defaultCommentDAO.findAll()
          await(existingCommentListFuture)
          existingCommentListFuture onComplete {
            case Success(list) => {
              list.contains(comment1) mustBe false
              list.contains(comment2) mustBe true
              val deleteFuture2 = defaultCommentDAO.deleteById(Comment2Id)
              await(deleteFuture2)
              deleteFuture2 onComplete {
                case Success(n) => {
                  n mustBe 1
                  val listFuture = defaultCommentDAO.findAll()
                  await(listFuture)
                  listFuture onComplete {
                    case Success(list) => list.size mustBe 0
                    case Failure(t) => fail("failed to retrieve CommentModel list for second time " + t)
                  }
                }
                case Failure(t) => fail("failed to delete second CommentModel " + t)
              }
            }
            case Failure(t) => fail("failed to retrieve list of CommentModels when testing delete " + t)
          }
        }
        case Failure(t) => fail("failed to delete first CommentModel " + t)
      }
    }
    
  }

}