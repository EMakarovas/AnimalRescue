package com.emakarovas.animalrescue.persistence.dao.impl

import org.scalatestplus.play.OneAppPerSuite

import com.emakarovas.animalrescue.model.CommentModel
import com.emakarovas.animalrescue.model.LocationModel
import com.emakarovas.animalrescue.model.UserModel
import com.emakarovas.animalrescue.model.enumeration.AnimalType
import com.emakarovas.animalrescue.model.enumeration.Gender
import com.emakarovas.animalrescue.model.property.UserUpdatableProperty
import com.emakarovas.animalrescue.persistence.dao.update.UpdatableModelContainer
import com.emakarovas.animalrescue.persistence.dao.update.UpdateStatus
import com.emakarovas.animalrescue.testutil.DelayedPlaySpec
import com.emakarovas.animalrescue.testutil.TestUtils

import play.api.test.Helpers.await
import play.api.test.Helpers.defaultAwaitTimeout

class DefaultUserDAOSpec extends DelayedPlaySpec with OneAppPerSuite {
  
//  private val CommonUserId = "user id 1"
//  
//  private var user1 = UserModel("id 1", "url 1", 
//      List(
//          UserAnimalModel("user animal id 1", AnimalType.Pig, Some("pink pig"), Gender.Female, Some(12), Some(24),
//              List("potential id 1", "potential id 2"),
//              Some(UserTerminationReasonModel(UserTerminationReason.FoundHere, Some("text 1"))))),
//      LocationModel("loc id 1", "country 1", "city 1", Some("street 1"), 15.5, 16.5),
//      List(
//          CommentModel("comment id 1", TestUtils.buildDate(3, 3, 2013), "comment text 1", Some("comment name 1"), 
//          Some("comment user id 1")),
//          CommentModel("comment id 2", TestUtils.buildDate(4, 4, 2014), "comment text 2", Some("comment name 2"), 
//          Some("comment user id 2"))),
//      TestUtils.buildDate(5, 5, 1975), Some(TestUtils.buildDate(6, 6, 1975)), true, CommonUserId)
//  private var user2 = UserModel("id 2", "url 2", 
//      List(), 
//      LocationModel("loc id 2", "country 2", "city 2", Some("street 2"), 25.5, 26.5),
//      List(),
//      TestUtils.buildDate(8, 8, 1988), None, false, "other user id")
//  private var user3 = UserModel("id 3", "url 3", 
//      List(
//          UserAnimalModel("user animal id 3", AnimalType.Hedgehog, Some("cutehog"), Gender.Unspecified, Some(32), Some(44),
//              List("potential id 3", "potential id 22"),
//              Some(UserTerminationReasonModel(UserTerminationReason.FoundElsewhere, Some("text 3"))))),
//      LocationModel("loc id 3", "country 3", "city 3", Some("street 3"), 35.5, 36.5),
//      List(
//          CommentModel("comment id 3", TestUtils.buildDate(3, 3, 2033), "comment text 3", Some("comment name 3"), 
//          Some("comment user id 3")),
//          CommentModel("comment id 5", TestUtils.buildDate(4, 4, 5034), "comment text 5", Some("comment name 5"), 
//          Some("comment user id 5"))),
//      TestUtils.buildDate(5, 5, 3975), Some(TestUtils.buildDate(6, 6, 3975)), true, CommonUserId)
//
//  lazy val defaultUserDAO = app.injector.instanceOf[DefaultUserDAO]
//    
//  import scala.concurrent.ExecutionContext.Implicits.global
//
//  "DefaultUserDAOSpec" should {
//    
//    "create a new UserModel by calling create()" in {
//      val crF = defaultUserDAO.create(user1)
//      await(crF)
//      crF onSuccess {
//        case n => n mustBe 1
//      }
//      val findF = crF.flatMap(_ => defaultUserDAO.findById(user1.id))
//      await(findF)
//      findF onSuccess {
//        case op => op.get mustBe user1
//      }
//    }
//    
//    "create a new UserModel by calling create(), even with some Nones" in {
//      val crF = defaultUserDAO.create(user2)
//      await(crF)
//      crF onSuccess {
//        case n => n mustBe 1
//      }
//      val findF = crF.flatMap(_ => defaultUserDAO.findById(user2.id))
//      await(findF)
//      findF onSuccess {
//        case userOpt => userOpt.get mustBe user2
//      }
//    }
//    
//    "after calling create(), return the UserModel in findById even before the save is done in the DB" in {
//      val crF = defaultUserDAO.create(user3)
//      val findF = defaultUserDAO.findById(user3.id)
//      await(findF)
//      findF onSuccess {
//        case opt => {
//          opt.isDefined mustBe true
//          opt.get mustBe user3
//        }
//      }
//    }
//    
//    "allow to update a UserModel if no properties have been updated individually" in {
//      delay()
//      val findF = defaultUserDAO.lockAndFindById(user1.id)
//      await(findF)
//      val updF = findF.flatMap(updateOpt => {
//        updateOpt.isDefined mustBe true
//        val user = updateOpt.get.model
//        user mustBe user1
//        user1 = user1.copy(url="updated url 1")
//        defaultUserDAO.update(UpdatableModelContainer[UserModel](user1, updateOpt.get.token))
//      })
//      await(updF)
//      val updatedFindF = updF.flatMap(updRes => {
//        updRes.updateStatus mustBe UpdateStatus.Executed
//        updRes.n mustBe 1
//        defaultUserDAO.findById(user1.id)
//      })
//      await(updatedFindF)
//      updatedFindF onSuccess {
//        case opt => {
//          opt.isDefined mustBe true
//          opt.get mustBe user1
//        }
//      }
//    }
//    
//    "update UserUpdatableProperty's that are not None's" in {
//      delay()
//      
//      user1 = user1.copy(
//          isPublic = !user1.isPublic
//      )
//      
//      val pubProp = UserUpdatableProperty.UserIsPublicProperty(user1.isPublic)
//      val updF1 = defaultUserDAO.updatePropertyById(user1.id, pubProp)
//      await(updF1)
//      
//      val findF = updF1.flatMap(_ => defaultUserDAO.findById(user1.id))
//      await(findF)
//      findF onSuccess {
//        case op: Option[UserModel] => {
//          op.get mustBe user1
//        }
//      }
//
//    }
//
//    "deny update request when a property has been updated during model manipulation" in {
//      delay()
//      // find the model and lock it
//      val findF = defaultUserDAO.lockAndFindById(user1.id)
//      await(findF)
//      // change model and issue a property update
//      user1 = user1.copy(isPublic = !user1.isPublic)
//      defaultUserDAO.updatePropertyById(user1.id, UserUpdatableProperty.UserIsPublicProperty(user1.isPublic))
//      // try to update
//      val modelUpdateF = findF.flatMap(
//          op => {
//            val token = op.get.token
//            defaultUserDAO.update(UpdatableModelContainer[UserModel](user2, token))
//          }
//      )
//      await(modelUpdateF)
//      modelUpdateF onSuccess {
//        case res => res.updateStatus mustBe UpdateStatus.Denied
//      }
//
//    }
//    
//    "insert UserInsertableCollectionProperty's" in {
//      delay()
//      val newComment = CommentModel("comment id 155", TestUtils.buildDate(3, 3, 2018), "comment text 14", Some("comment name 12"), 
//          Some("comment user id 15555"))
//      val newUserAnimal = UserAnimalModel("user animal id 16", AnimalType.Pig, Some("pink pig"), 
//          Gender.Female, Some(12), Some(24),
//          List("potential id 1", "potential id 2"),
//          Some(UserTerminationReasonModel(UserTerminationReason.FoundHere, Some("text 1"))))
//      user1 = user1.copy(
//          commentList = user1.commentList ::: List(newComment),
//          userAnimalList = user1.userAnimalList ::: List(newUserAnimal)
//      )
//      
//      val comProp = UserInsertableCollectionProperty.UserCommentInsertableCollectionProperty(newComment)
//      val updF1 = defaultUserDAO.insertCollectionPropertyById(user1.id, comProp)
//      
//      val anProp = UserInsertableCollectionProperty.UserUserAnimalInsertableCollectionProperty(newUserAnimal)
//      val updF2 = defaultUserDAO.insertCollectionPropertyById(user1.id, anProp)
//      
//      val compF = for {
//        f1 <- updF1
//        f2 <- updF2
//      } yield(f1 + f2)
//      await(compF)
//      
//      val findF = compF.flatMap(_ => defaultUserDAO.findById(user1.id))
//      await(findF)
//      findF onSuccess {
//        case op => op.get mustBe user1
//      }
//    }
//    
//    "update UserUpdatableCollectionProperty's that are not None's" in {
//      delay()
//      
//      val oldAnimal = user1.userAnimalList(0)
//      val updatedUserAnimal = oldAnimal.copy(
//          specificType = Some("updated specific type of animal"),
//          gender = Gender.Unspecified,
//          minAge = Some(155),
//          maxAge = Some(255)
//      )
//      val oldList = user1.userAnimalList
//      val newList = List(updatedUserAnimal) ::: oldList.slice(1, oldList.size)
//      
//      user1 = user1.copy(
//          userAnimalList = newList
//      )
//      
//      val specTypeProp = UserUpdatableCollectionProperty.UserAnimalListSpecificTypeProperty(
//          updatedUserAnimal.id, updatedUserAnimal.specificType)
//      val updF1 = defaultUserDAO.updateCollectionPropertyById(user1.id, specTypeProp)
//      
//      val genProp = UserUpdatableCollectionProperty.UserAnimalListGenderProperty(
//          updatedUserAnimal.id, updatedUserAnimal.gender)
//      val updF2 = defaultUserDAO.updateCollectionPropertyById(user1.id, genProp)
//      
//      val minAgeProp = UserUpdatableCollectionProperty.UserAnimalListMinAgeProperty(
//          updatedUserAnimal.id, updatedUserAnimal.minAge)
//      val updF3 = defaultUserDAO.updateCollectionPropertyById(user1.id, minAgeProp)
//      
//      val maxAgeProp = UserUpdatableCollectionProperty.UserAnimalListMaxAgeProperty(
//          updatedUserAnimal.id, updatedUserAnimal.maxAge)
//      val updF4 = defaultUserDAO.updateCollectionPropertyById(user1.id, maxAgeProp)
//      
//      val compF = for {
//        f1 <- updF1
//        f2 <- updF2
//        f3 <- updF3
//        f4 <- updF4
//      } yield(f1 + f2 + f3 + f4)
//      await(compF)
//      
//      val findF = compF.flatMap(_ => defaultUserDAO.findById(user1.id))
//      await(findF)
//      findF onSuccess {
//        case op => op.get mustBe user1
//      }
//    }
//    
//    "update UserUpdatableCollectionProperty's that are None's" in {
//      delay()
//      
//      val oldAnimal = user1.userAnimalList(0)
//      val updatedUserAnimal = oldAnimal.copy(
//          specificType = None,
//          minAge = None,
//          maxAge = None
//      )
//      val oldList = user1.userAnimalList
//      val newList = List(updatedUserAnimal) ::: oldList.slice(1, oldList.size)
//      
//      user1 = user1.copy(
//          userAnimalList = newList
//      )
//      
//      val specTypeProp = UserUpdatableCollectionProperty.UserAnimalListSpecificTypeProperty(
//          updatedUserAnimal.id, updatedUserAnimal.specificType)
//      val updF1 = defaultUserDAO.updateCollectionPropertyById(user1.id, specTypeProp)
//      
//      val minAgeProp = UserUpdatableCollectionProperty.UserAnimalListMinAgeProperty(
//          updatedUserAnimal.id, updatedUserAnimal.minAge)
//      val updF2 = defaultUserDAO.updateCollectionPropertyById(user1.id, minAgeProp)
//      
//      val maxAgeProp = UserUpdatableCollectionProperty.UserAnimalListMaxAgeProperty(
//          updatedUserAnimal.id, updatedUserAnimal.maxAge)
//      val updF3 = defaultUserDAO.updateCollectionPropertyById(user1.id, maxAgeProp)
//      
//      val compF = for {
//        f1 <- updF1
//        f2 <- updF2
//        f3 <- updF3
//      } yield(f1 + f2 + f3)
//      await(compF)
//      
//      val findF = compF.flatMap(_ => defaultUserDAO.findById(user1.id))
//      await(findF)
//      findF onSuccess {
//        case op => op.get mustBe user1
//      }
//    }
//    
//    "delete UserDeletableProperty's" in {
//      delay()
//      
//      val deletedAnimalId = user1.userAnimalList(0).id
//      user1 = user1.copy(
//          userAnimalList = user1.userAnimalList.slice(1, user1.userAnimalList.size)
//      )
//      
//      val prop = UserDeletableCollectionProperty.UserUserAnimalDeletableCollectionProperty(deletedAnimalId)
//      val updF = defaultUserDAO.deleteCollectionPropertyById(user1.id, prop)
//      await(updF)
//      val findF = updF.flatMap(_ => defaultUserDAO.findById(user1.id))
//      await(findF)
//      findF onSuccess {
//        case op => op.get mustBe user1
//      }
//    }
//    
//    "add to potentialAnimalIdList when addToPotentialAnimalIdListByUserAnimalId is called if it doesn't exist yet" in {
//      delay()
//      val addedId = "added id"
//      val oldAnimal = user1.userAnimalList(0)
//      val updatedAnimal = oldAnimal.copy(
//          potentialAnimalIdList = oldAnimal.potentialAnimalIdList ::: List(addedId)
//      )
//      val oldList = user1.userAnimalList
//      val updatedList = List(updatedAnimal) ::: oldList.slice(1, oldList.size)
//      user1 = user1.copy(
//          userAnimalList = updatedList
//      )
//      val addF = defaultUserDAO.addToPotentialAnimalIdListByUserAnimalId(user1.id, updatedAnimal.id, addedId)
//      await(addF)
//      val findF = addF.flatMap(n => {
//        n mustBe 1 
//        defaultUserDAO.findById(user1.id)
//      })
//      await(findF)
//      val addF2 = findF.flatMap(op => {
//        op.get mustBe user1
//        defaultUserDAO.addToPotentialAnimalIdListByUserAnimalId(user1.id, updatedAnimal.id, addedId)
//      })
//      await(addF2)
//      val findF2 = addF2.flatMap(n => {
//        n mustBe 0
//        defaultUserDAO.findById(user1.id)
//      })
//      await(findF2)
//      findF2 onSuccess {
//        // the retrieved user must be the same, i.e. have the same list length
//        case op => op.get mustBe user1
//      }
//    }
//    
//    "find a list of all UserModels when findAll() is called" in {
//      delay()
//      val findF = defaultUserDAO.findAll()
//      await(findF)
//      findF onSuccess {
//        case list => {
//          list.size mustBe 3
//          list.contains(user1) mustBe true
//          list.contains(user2) mustBe true
//          list.contains(user3) mustBe true
//        }
//      }
//    }
//    
//    "find UserModels when findByUserId() is called" in {
//      delay()
//      val findF = defaultUserDAO.findByUserId(CommonUserId)
//      await(findF)
//      findF onSuccess {
//        case list => {
//          list.size mustBe 2
//          list.contains(user1) mustBe true
//          list.contains(user3) mustBe true
//        }
//      }
//    }
//    
//    "delete UserModels when deleteById() is called" in {
//      delay()
//      val singleDeleteF = defaultUserDAO.deleteById(user1.id)
//      await(singleDeleteF)
//      val findF = singleDeleteF.flatMap(_ => defaultUserDAO.findAll())
//      await(findF)
//      findF onSuccess {
//        case list => {
//          list.size mustBe 2
//          list.contains(user2) mustBe true
//          list.contains(user3) mustBe true
//        }
//      }
//      val delF2 = findF.flatMap(_ => defaultUserDAO.deleteById(user2.id))
//      val delF3 = findF.flatMap(_ => defaultUserDAO.deleteById(user3.id))
//      val allDelF = for {
//        f2 <- delF2
//        f3 <- delF3
//      } yield(f2 + f3)
//      await(allDelF)
//      val findF2 = allDelF.flatMap(_ => defaultUserDAO.findAll())
//      await(findF2)
//      findF2 onSuccess {
//        case list => list.size mustBe 0
//      }
//    }
//    
//  }
  
}