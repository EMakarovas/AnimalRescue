package com.emakarovas.animalrescue.persistence.dao.impl

import org.scalatestplus.play.OneAppPerSuite

import com.emakarovas.animalrescue.model.CommentModel
import com.emakarovas.animalrescue.model.Location
import com.emakarovas.animalrescue.model.UserModel
import com.emakarovas.animalrescue.model.enumeration.AnimalType
import com.emakarovas.animalrescue.model.enumeration.Gender
import com.emakarovas.animalrescue.model.property.UserUpdatableProperty
import com.emakarovas.animalrescue.persistence.dao.update.VersionedModelContainer
import com.emakarovas.animalrescue.testutil.DelayedPlaySpec
import com.emakarovas.animalrescue.testutil.TestUtils

import play.api.test.Helpers.await
import play.api.test.Helpers.defaultAwaitTimeout
import com.emakarovas.animalrescue.model.ImageModel
import com.emakarovas.animalrescue.model.Geolocation
import com.emakarovas.animalrescue.model.PersonModel
import com.emakarovas.animalrescue.model.AccountSettingsModel

class DefaultUserDAOSpec extends DelayedPlaySpec with OneAppPerSuite {
  
//  private var user1 = UserModel("id 1", "email 1", "psw 1", Some("salt 1"), Some("act str 1"), Some("psw str 1"),
//      Some(PersonModel(
//          "person id 1", "name 1", Some("surname 1"), Gender.Unspecified, Some("phone 1"),
//          Some(ImageModel("img id 1", "img url 1")),
//          LocationModel("loc id 1", "country 1", "city 1", Some("street 1"), GeolocationModel(1.1, 1.2)))),
//      AccountSettingsModel(true))
//  private var user2 = UserModel("id 2", "email 2", "psw 2", None, None, None,
//      None,
//      AccountSettingsModel(true))
//  private var user3 = UserModel("id 3", "email 3", "psw 3", Some("salt 3"), Some("act str 3"), Some("psw str 3"),
//      Some(PersonModel(
//          "person id 3", "name 3", Some("surname 3"), Gender.Female, Some("phone 3"),
//          Some(ImageModel("img id 3", "img url 3")),
//          LocationModel("loc id 3", "country 3", "city 3", Some("street 3"), GeolocationModel(3.3, 3.2)))),
//      AccountSettingsModel(true))
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
//      val findF = defaultUserDAO.findUpdatableById(user1.id)
//      await(findF)
//      val updF = findF.flatMap(updateOpt => {
//        updateOpt.isDefined mustBe true
//        val user = updateOpt.get.model
//        user mustBe user1
//        user1 = user1.copy(salt=Some("different salt"))
//        defaultUserDAO.update(VersionedModelContainer[UserModel](user1, updateOpt.get.version))
//      })
//      await(updF)
//      val updatedFindF = updF.flatMap(n => {
//        n mustBe 1
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
//      val oldPerson = user1.person
//      val newPerson = oldPerson.get.copy(
//          name = "updated name",
//          surname = Some("updated surname"),
//          gender = Gender.Male,
//          phoneNumber = Some("updated phone number"),
//          image = Some(ImageModel("updated img id", "updated img url"))
//      )
//      
//      user1 = user1.copy(
//          person = Some(newPerson),
//          settings = AccountSettingsModel(false)
//      )
//      
//      val nameProp = UserUpdatableProperty.PersonName(user1.person.get.name)
//      val updF1 = defaultUserDAO.updatePropertyById(user1.id, nameProp)
//      
//      val surnameProp = UserUpdatableProperty.PersonSurname(user1.person.get.surname)
//      val updF2 = defaultUserDAO.updatePropertyById(user1.id, surnameProp)
//      
//      val genderProp = UserUpdatableProperty.PersonGender(user1.person.get.gender)
//      val updF3 = defaultUserDAO.updatePropertyById(user1.id, genderProp)
//      
//      val phoneProp = UserUpdatableProperty.PersonPhoneNumber(user1.person.get.phoneNumber)
//      val updF4 = defaultUserDAO.updatePropertyById(user1.id, phoneProp)
//      
//      val imgProp = UserUpdatableProperty.PersonImage(user1.person.get.image)
//      val updF5 = defaultUserDAO.updatePropertyById(user1.id, imgProp)
//      
//      val settingsProp = UserUpdatableProperty.SettingsSendEmailsWithMatches(user1.settings.sendEmailsWithMatches)
//      val updF6 = defaultUserDAO.updatePropertyById(user1.id, settingsProp)
//
//      val compF = for {
//        u1 <- updF1
//        u2 <- updF2
//        u3 <- updF3
//        u4 <- updF4
//        u5 <- updF5
//        u6 <- updF6
//      } yield(u1 + u2 + u3 + u4 + u5 + u6)
//      await(compF)
//      
//      val findF = compF.flatMap(_ => defaultUserDAO.findById(user1.id))
//      await(findF)
//      findF onSuccess {
//        case op: Option[UserModel] => {
//          op.get mustBe user1
//        }
//      }
//
//    }
//    
//    "update UserUpdatableProperty's that are None's" in {
//      delay()
//      
//      val oldPerson = user1.person
//      val newPerson = oldPerson.get.copy(
//          surname = None,
//          phoneNumber = None,
//          image = None
//      )
//      
//      user1 = user1.copy(
//          person = Some(newPerson)
//      )
//      
//      val surnameProp = UserUpdatableProperty.PersonSurname(user1.person.get.surname)
//      val updF1 = defaultUserDAO.updatePropertyById(user1.id, surnameProp)
//      
//      val phoneProp = UserUpdatableProperty.PersonPhoneNumber(user1.person.get.phoneNumber)
//      val updF2 = defaultUserDAO.updatePropertyById(user1.id, phoneProp)
//      
//      val imgProp = UserUpdatableProperty.PersonImage(user1.person.get.image)
//      val updF3 = defaultUserDAO.updatePropertyById(user1.id, imgProp)
//
//      val compF = for {
//        u1 <- updF1
//        u2 <- updF2
//        u3 <- updF3
//      } yield(u1 + u2 + u3)
//      await(compF)
//      
//      val findF = compF.flatMap(_ => defaultUserDAO.findById(user1.id))
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
//      val findF = defaultUserDAO.findUpdatableById(user1.id)
//      await(findF)
//      // change model and issue a property update
//      user1 = user1.copy(
//          person = Some(user1.person.get.copy(
//              name = "different name once again"
//          ))
//      )
//      defaultUserDAO.updatePropertyById(user1.id, UserUpdatableProperty.PersonName(user1.person.get.name))
//      // try to update
//      val modelUpdateF = findF.flatMap(
//          op => {
//            val token = op.get.version
//            defaultUserDAO.update(VersionedModelContainer[UserModel](user2, token))
//          }
//      )
//      await(modelUpdateF)
//      modelUpdateF onSuccess {
//        case n => n mustBe 0
//      }
//
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
//    "find UserModels when findByEmail() is called" in {
//      delay()
//      val findF = defaultUserDAO.findByEmail(user1.email)
//      await(findF)
//      findF onSuccess {
//        case list => {
//          list.get mustBe user1
//        }
//      }
//      val findF2 = defaultUserDAO.findByEmail(user2.email)
//      await(findF2)
//      findF2 onSuccess {
//        case list => {
//          list.get mustBe user2
//        }
//      }
//      val findF3 = defaultUserDAO.findByEmail(user3.email)
//      await(findF3)
//      findF3 onSuccess {
//        case list => {
//          list.get mustBe user3
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