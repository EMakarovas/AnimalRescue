package com.emakarovas.animalrescue.persistence.dao.impl

import org.scalatestplus.play.OneAppPerSuite
import org.scalatestplus.play.PlaySpec
import play.api.test.Helpers
import org.scalatestplus.play._
import play.api.test._
import play.api.test.Helpers._

import com.emakarovas.animalrescue.model.CostModel
import javax.inject.Inject
import scala.util.Failure
import scala.util.Success
import com.emakarovas.animalrescue.model.common.Gender
import com.emakarovas.animalrescue.model.cost.CostType
import com.emakarovas.animalrescue.model.CostModel

class DefaultCostDAOSpec extends PlaySpec with OneAppPerSuite {
  
  val Cost1Id = "cost id"
  val Cost1Type = CostType.Food
  val Cost1Amount = 55.00
  val Cost2Id = "cost2 id"
  val Cost2Type = CostType.Vaccination
  val Cost2Amount = 5.00
  val Cost1AmountUpdated = 115.00
  
  val cost1 = new CostModel(Cost1Id, Cost1Type, Cost1Amount)
  val cost2 = new CostModel(Cost2Id, Cost2Type, Cost2Amount)
  val updatedCost = new CostModel(Cost1Id, Cost1Type, Cost1AmountUpdated)
  lazy val defaultCostDAO: DefaultCostDAO = app.injector.instanceOf[DefaultCostDAO]
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  "DefaultCostDAO" should {
    
//    "store a new CostModel in the DB when create is called" in {
//      val f = defaultCostDAO.create(cost1)
//      await(f)
//      f onComplete {
//        case Success(n) => n mustBe 1
//        case Failure(t) => fail("failed to create new CostModel " + t)
//      }
//    }
//    
    "find the correct CostModel from the DB when find is called" in {
      val retrievedCost = defaultCostDAO.findById(Cost1Id)
      await(retrievedCost)
      retrievedCost onComplete {
        case Success(option) => option.get mustBe cost1
        case Failure(t) => fail("failed to retrieve the CostModel " + t)
      }
    }
    
//    "find the list of all CostModels when findAll is called" in {
//      val saveFuture = defaultCostDAO.create(cost2)
//      await(saveFuture)
//      saveFuture onComplete {
//        case Success(n) => {
//          val listFuture = defaultCostDAO.findAll()
//          await(listFuture)
//          listFuture onComplete {
//            case Success(list) => list.contains(cost1) mustBe true; list.contains(cost2) mustBe true
//            case Failure(t) => fail("failed to retrieve list of CostModels " + t)
//          }
//        }
//        case Failure(t) => fail("failed to save second CostModel in DB " + t)
//      }
//    }
//    
//    "update a CostModel when update is called" in {
//      val updateFuture = defaultCostDAO.update(updatedCost)
//      await(updateFuture)
//      updateFuture onComplete {
//        case Success(n) => {
//          n mustBe 1
//          val updatedCostFuture = defaultCostDAO.findById(Cost1Id)
//          await(updatedCostFuture)
//          updatedCostFuture onComplete {
//            case Success(costOption) => costOption.get.amount mustBe Cost1AmountUpdated
//            case Failure(t) => fail("failed to retrieve updated CostModel " + t)
//          }
//        }
//        case Failure(t) => fail("failed to update CostModel " + t)
//      }
//    }
//    
//    "delete a CostModel when deleteById is called" in {
//      val deleteFuture = defaultCostDAO.deleteById(Cost1Id)
//      await(deleteFuture)
//      deleteFuture onComplete {
//        case Success(n) => {
//          n mustBe 1
//          val existingCostListFuture = defaultCostDAO.findAll()
//          await(existingCostListFuture)
//          existingCostListFuture onComplete {
//            case Success(list) => {
//              list.contains(cost1) mustBe false
//              list.contains(cost2) mustBe true
//              val deleteFuture2 = defaultCostDAO.deleteById(Cost2Id)
//              await(deleteFuture2)
//              deleteFuture2 onComplete {
//                case Success(n) => {
//                  n mustBe 1
//                  val listFuture = defaultCostDAO.findAll()
//                  await(listFuture)
//                  listFuture onComplete {
//                    case Success(list) => list.size mustBe 0
//                    case Failure(t) => fail("failed to retrieve CostModel list for second time " + t)
//                  }
//                }
//                case Failure(t) => fail("failed to delete second CostModel " + t)
//              }
//            }
//            case Failure(t) => fail("failed to retrieve list of CostModels when testing delete " + t)
//          }
//        }
//        case Failure(t) => fail("failed to delete first CostModel " + t)
//      }
//    }
    
  }
  
}