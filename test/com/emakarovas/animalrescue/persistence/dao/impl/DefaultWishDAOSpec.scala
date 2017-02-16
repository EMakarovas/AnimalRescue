package com.emakarovas.animalrescue.persistence.dao.impl

import scala.util.Failure
import scala.util.Success

import org.scalatestplus.play.OneAppPerSuite

import com.emakarovas.animalrescue.model.WishModel
import com.emakarovas.animalrescue.model.enumeration.AnimalType
import com.emakarovas.animalrescue.model.enumeration.Gender
import com.emakarovas.animalrescue.testutil.DelayedPlaySpec

import play.api.test.Helpers.await
import play.api.test.Helpers.defaultAwaitTimeout
import com.emakarovas.animalrescue.model.enumeration.Gender
import com.emakarovas.animalrescue.model.ImageModel
import com.emakarovas.animalrescue.model.GeolocationModel
import com.emakarovas.animalrescue.testutil.TestUtils

class DefaultWishDAOSpec extends DelayedPlaySpec with OneAppPerSuite {
  
  val Wish1Id = "animal id"
  val Wish1AnimalType = AnimalType.Bird
  val Wish1SpecificType = Some("Parrot")
  val Wish1Gender = Gender.Female
  val Wish1MinAge = Some(24)
  val Wish1MaxAge = Some(48)
  val Wish1Geolocation = GeolocationModel("geoloc id", 1, 1)
  val Wish1StartDate = TestUtils.buildDate(2016, 6, 6)
  val Wish1EndDate = Some(TestUtils.buildDate(2016, 7, 7))
  val Wish1UserId = "user id"
  
  val Wish2Id = "animal 2id"
  val Wish2AnimalType = AnimalType.Chinchilla
  val Wish2SpecificType = None
  val Wish2Gender = Gender.Unspecified
  val Wish2MinAge = Some(24)
  val Wish2MaxAge = None
  val Wish2Geolocation = GeolocationModel("geoloc id", 2, 2)
  val Wish2StartDate = TestUtils.buildDate(2026, 6, 6)
  val Wish2EndDate = None
  val Wish2UserId = "user2 id"
   
  val Wish2GenderUpdated = Gender.Male

  val wish1 = WishModel(Wish1Id, Wish1AnimalType, Wish1SpecificType, Wish1Gender, Wish1MinAge, Wish1MaxAge, 
      Wish1Geolocation, Wish1StartDate, Wish1EndDate, Wish1UserId)
  val wish2 = WishModel(Wish2Id, Wish2AnimalType, Wish2SpecificType, Wish2Gender, Wish2MinAge, Wish2MaxAge, 
      Wish2Geolocation, Wish2StartDate, Wish2EndDate, Wish2UserId)
  val updatedWish = WishModel(Wish2Id, Wish2AnimalType, Wish2SpecificType, Wish2GenderUpdated, Wish2MinAge, Wish2MaxAge, 
      Wish2Geolocation, Wish2StartDate, Wish2EndDate, Wish2UserId)
      
  lazy val defaultWishDAO: DefaultWishDAO = app.injector.instanceOf[DefaultWishDAO]
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  "DefaultWishDAO" should {
    
    "store a new WishModel in the DB when create is called" in {
      val f = defaultWishDAO.create(wish1)
      await(f)
      f onComplete {
        case Success(n) => n mustBe 1
        case Failure(t) => fail("failed to create new WishModel " + t)
      }
    }
    
    "correctly store a new WishModel in the DB when create is called, even with some Nones" in {
      delay
      val f = defaultWishDAO.create(wish2)
      await(f)
      f onComplete {
        case Success(n) => n mustBe 1
        case Failure(t) => fail("failed to create new WishModel " + t)
      }
    }
    
    "find the correct WishModel from the DB when find is called" in {
      delay
      val retrievedWish = defaultWishDAO.findById(Wish1Id)
      await(retrievedWish)
      retrievedWish onComplete {
        case Success(option) => option.get mustBe wish1
        case Failure(t) => fail("failed to retrieve the WishModel " + t)
      }
    }
    
    "find the correct WishModel from the DB when find is called, even with some Nones" in {
      delay
      val retrievedWish = defaultWishDAO.findById(Wish2Id)
      await(retrievedWish)
      retrievedWish onComplete {
        case Success(option) => option.get mustBe wish2; option.get.gender mustBe Gender.Unspecified
        case Failure(t) => fail("failed to retrieve the WishModel " + t)
      }
    }
    
    "find the correct WishModel from the DB when findByUserId is called" in {
      delay
      val retrievedWish = defaultWishDAO.findByUserId(Wish1UserId)
      await(retrievedWish)
      retrievedWish onComplete {
        case Success(list) => list.size mustBe 1; list.contains(wish1) mustBe true
        case Failure(t) => fail("failed to retrieve the WishModel " + t)
      }
    }

    "find the list of all WishModels when findAll is called" in {
      delay
      val listFuture = defaultWishDAO.findAll()
      await(listFuture)
      listFuture onComplete {
        case Success(list) => list.size mustBe 2; list.contains(wish1) mustBe true; list.contains(wish2) mustBe true
        case Failure(t) => fail("failed to retrieve list of WishModels " + t)
      }
    }
    
    "update a WishModel when update is called" in {
      delay
      val updateFuture = defaultWishDAO.update(updatedWish)
      await(updateFuture)
      updateFuture onComplete {
        case Success(n) => {
          n mustBe 1
          delay
          val updatedWishFuture = defaultWishDAO.findById(Wish2Id)
          await(updatedWishFuture)
          updatedWishFuture onComplete {
            case Success(animalOption) => animalOption.get.gender mustBe Wish2GenderUpdated
            case Failure(t) => fail("failed to retrieve updated WishModel " + t)
          }
        }
        case Failure(t) => fail("failed to update WishModel " + t)
      }
    }
    
    "delete a WishModel when deleteById is called" in {
      delay
      val deleteFuture = defaultWishDAO.deleteById(Wish1Id)
      await(deleteFuture)
      deleteFuture onComplete {
        case Success(n) => {
          n mustBe 1
          val existingWishListFuture = defaultWishDAO.findAll()
          await(existingWishListFuture)
          existingWishListFuture onComplete {
            case Success(list) => {
              list.contains(wish1) mustBe false
              list.contains(updatedWish) mustBe true
              val deleteFuture2 = defaultWishDAO.deleteById(Wish2Id)
              await(deleteFuture2)
              deleteFuture2 onComplete {
                case Success(n) => {
                  n mustBe 1
                  val listFuture = defaultWishDAO.findAll()
                  await(listFuture)
                  listFuture onComplete {
                    case Success(list) => list.size mustBe 0
                    case Failure(t) => fail("failed to retrieve WishModel list for second time " + t)
                  }
                }
                case Failure(t) => fail("failed to delete second WishModel " + t)
              }
            }
            case Failure(t) => fail("failed to retrieve list of WishModels when testing delete " + t)
          }
        }
        case Failure(t) => fail("failed to delete first WishModel " + t)
      }
    }
    
  }

}