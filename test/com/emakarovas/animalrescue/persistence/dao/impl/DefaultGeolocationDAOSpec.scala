package com.emakarovas.animalrescue.persistence.dao.impl

import scala.util.Failure
import scala.util.Success

import org.scalatestplus.play.OneAppPerSuite

import com.emakarovas.animalrescue.model.GeolocationModel
import com.emakarovas.animalrescue.testutil.DelayedPlaySpec

import play.api.test.Helpers.await
import play.api.test.Helpers.defaultAwaitTimeout

class DefaultGeolocationDAOSpec extends DelayedPlaySpec with OneAppPerSuite {
  
  val Geolocation1Id = "geolocation id"
  val Geolocation1Lat = -0.0005
  val Geolocation1Lng = 55.00
  val Geolocation2Id = "geolocation2 id"
  val Geolocation2Lat = 0.0005
  val Geolocation2Lng = 25.6666
  val Geolocation1LatUpdated = 28.64
  
  val geolocation1 = new GeolocationModel(Geolocation1Id, Geolocation1Lat, Geolocation1Lng)
  val geolocation2 = new GeolocationModel(Geolocation2Id, Geolocation2Lat, Geolocation2Lng)
  val updatedGeolocation = new GeolocationModel(Geolocation1Id, Geolocation1LatUpdated, Geolocation1Lng)
  lazy val defaultGeolocationDAO: DefaultGeolocationDAO = app.injector.instanceOf[DefaultGeolocationDAO]
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  "DefaultGeolocationDAO" should {
    
    "store a new GeolocationModel in the DB when create is called" in {
      val f = defaultGeolocationDAO.create(geolocation1)
      await(f)
      f onComplete {
        case Success(n) => n mustBe 1
        case Failure(t) => fail("failed to create new GeolocationModel " + t)
      }
    }
    
    "find the correct GeolocationModel from the DB when find is called" in {
      delay
      val retrievedGeolocation = defaultGeolocationDAO.findById(Geolocation1Id)
      await(retrievedGeolocation)
      retrievedGeolocation onComplete {
        case Success(option) => option.get mustBe geolocation1
        case Failure(t) => fail("failed to retrieve the GeolocationModel " + t)
      }
    }
    
    "find the list of all GeolocationModels when findAll is called" in {
      delay
      val saveFuture = defaultGeolocationDAO.create(geolocation2)
      await(saveFuture)
      saveFuture onComplete {
        case Success(n) => {
          val listFuture = defaultGeolocationDAO.findAll()
          await(listFuture)
          listFuture onComplete {
            case Success(list) => list.contains(geolocation1) mustBe true; list.contains(geolocation2) mustBe true
            case Failure(t) => fail("failed to retrieve list of GeolocationModels " + t)
          }
        }
        case Failure(t) => fail("failed to save second GeolocationModel in DB " + t)
      } 
    }
    
    "update a GeolocationModel when update is called" in {
      delay
      val updateFuture = defaultGeolocationDAO.update(updatedGeolocation)
      await(updateFuture)
      updateFuture onComplete {
        case Success(n) => {
          n mustBe 1
          val updatedGeolocationFuture = defaultGeolocationDAO.findById(Geolocation1Id)
          await(updatedGeolocationFuture)
          updatedGeolocationFuture onComplete {
            case Success(geolocationOption) => geolocationOption.get.latitude mustBe Geolocation1LatUpdated
            case Failure(t) => fail("failed to retrieve updated GeolocationModel " + t)
          }
        }
        case Failure(t) => fail("failed to update GeolocationModel " + t)
      }
    }
    
    "delete a GeolocationModel when deleteById is called" in {
      delay
      val deleteFuture = defaultGeolocationDAO.deleteById(Geolocation1Id)
      await(deleteFuture)
      deleteFuture onComplete {
        case Success(n) => {
          n mustBe 1
          val existingGeolocationListFuture = defaultGeolocationDAO.findAll()
          await(existingGeolocationListFuture)
          existingGeolocationListFuture onComplete {
            case Success(list) => {
              list.contains(geolocation1) mustBe false
              list.contains(geolocation2) mustBe true
              val deleteFuture2 = defaultGeolocationDAO.deleteById(Geolocation2Id)
              await(deleteFuture2)
              deleteFuture2 onComplete {
                case Success(n) => {
                  n mustBe 1
                  val listFuture = defaultGeolocationDAO.findAll()
                  await(listFuture)
                  listFuture onComplete {
                    case Success(list) => list.size mustBe 0
                    case Failure(t) => fail("failed to retrieve GeolocationModel list for second time " + t)
                  }
                }
                case Failure(t) => fail("failed to delete second GeolocationModel " + t)
              }
            }
            case Failure(t) => fail("failed to retrieve list of GeolocationModels when testing delete " + t)
          }
        }
        case Failure(t) => fail("failed to delete first GeolocationModel " + t)
      }
    }
    
  }

}