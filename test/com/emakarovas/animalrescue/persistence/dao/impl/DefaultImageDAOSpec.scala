package com.emakarovas.animalrescue.persistence.dao.impl

import scala.util.Failure
import scala.util.Success

import org.scalatestplus.play.OneAppPerSuite

import com.emakarovas.animalrescue.model.ImageModel
import com.emakarovas.animalrescue.testutil.DelayedPlaySpec

import play.api.test.Helpers.await
import play.api.test.Helpers.defaultAwaitTimeout

class DefaultImageDAOSpec extends DelayedPlaySpec with OneAppPerSuite {
  
  val Image1Id = "image id"
  val Image1Url = "image 1 url"
  val Image2Id = "image2 id"
  val Image2Url = "image 2 url"
  val Image1UrlUpdated = "updated url"
  
  val image1 = new ImageModel(Image1Id, Image1Url)
  val image2 = new ImageModel(Image2Id, Image2Url)
  val updatedImage = new ImageModel(Image1Id, Image1UrlUpdated)
  lazy val defaultImageDAO: DefaultImageDAO = app.injector.instanceOf[DefaultImageDAO]
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  "DefaultImageDAO" should {
    
    "store a new ImageModel in the DB when create is called" in {
      val f = defaultImageDAO.create(image1)
      await(f)
      f onComplete {
        case Success(n) => n mustBe 1
        case Failure(t) => fail("failed to create new ImageModel " + t)
      }
    }
    
    "find the correct ImageModel from the DB when find is called" in {
      delay
      val retrievedImage = defaultImageDAO.findById(Image1Id)
      await(retrievedImage)
      retrievedImage onComplete {
        case Success(option) => option.get mustBe image1
        case Failure(t) => fail("failed to retrieve the ImageModel " + t)
      }
    }
    
    "find the list of all ImageModels when findAll is called" in {
      delay
      val saveFuture = defaultImageDAO.create(image2)
      await(saveFuture)
      saveFuture onComplete {
        case Success(n) => {
          val listFuture = defaultImageDAO.findAll()
          await(listFuture)
          listFuture onComplete {
            case Success(list) => list.contains(image1) mustBe true; list.contains(image2) mustBe true
            case Failure(t) => fail("failed to retrieve list of ImageModels " + t)
          }
        }
        case Failure(t) => fail("failed to save second ImageModel in DB " + t)
      } 
    }
    
    "update a ImageModel when update is called" in {
      delay
      val updateFuture = defaultImageDAO.update(updatedImage)
      await(updateFuture)
      updateFuture onComplete {
        case Success(n) => {
          n mustBe 1
          val updatedImageFuture = defaultImageDAO.findById(Image1Id)
          await(updatedImageFuture)
          updatedImageFuture onComplete {
            case Success(imageOption) => imageOption.get.url mustBe Image1UrlUpdated
            case Failure(t) => fail("failed to retrieve updated ImageModel " + t)
          }
        }
        case Failure(t) => fail("failed to update ImageModel " + t)
      }
    }
    
    "delete a ImageModel when deleteById is called" in {
      delay
      val deleteFuture = defaultImageDAO.deleteById(Image1Id)
      await(deleteFuture)
      deleteFuture onComplete {
        case Success(n) => {
          n mustBe 1
          val existingImageListFuture = defaultImageDAO.findAll()
          await(existingImageListFuture)
          existingImageListFuture onComplete {
            case Success(list) => {
              list.contains(image1) mustBe false
              list.contains(image2) mustBe true
              val deleteFuture2 = defaultImageDAO.deleteById(Image2Id)
              await(deleteFuture2)
              deleteFuture2 onComplete {
                case Success(n) => {
                  n mustBe 1
                  val listFuture = defaultImageDAO.findAll()
                  await(listFuture)
                  listFuture onComplete {
                    case Success(list) => list.size mustBe 0
                    case Failure(t) => fail("failed to retrieve ImageModel list for second time " + t)
                  }
                }
                case Failure(t) => fail("failed to delete second ImageModel " + t)
              }
            }
            case Failure(t) => fail("failed to retrieve list of ImageModels when testing delete " + t)
          }
        }
        case Failure(t) => fail("failed to delete first ImageModel " + t)
      }
    }
    
  }

}