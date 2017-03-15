package com.emakarovas.animalrescue.persistence.dao.impl

import org.scalatestplus.play.OneAppPerSuite

import com.emakarovas.animalrescue.model.CommentModel
import com.emakarovas.animalrescue.model.GeolocationModel
import com.emakarovas.animalrescue.model.LocationModel
import com.emakarovas.animalrescue.model.SearchAnimalModel
import com.emakarovas.animalrescue.model.SearchModel
import com.emakarovas.animalrescue.model.SearchTerminationReasonModel
import com.emakarovas.animalrescue.model.enumeration.AnimalType
import com.emakarovas.animalrescue.model.enumeration.Gender
import com.emakarovas.animalrescue.model.enumeration.SearchTerminationReason
import com.emakarovas.animalrescue.model.property.SearchDeletableCollectionProperty
import com.emakarovas.animalrescue.model.property.SearchInsertableCollectionProperty
import com.emakarovas.animalrescue.model.property.SearchUpdatableCollectionProperty
import com.emakarovas.animalrescue.model.property.SearchUpdatableProperty
import com.emakarovas.animalrescue.persistence.dao.update.VersionedModelContainer
import com.emakarovas.animalrescue.testutil.DelayedPlaySpec
import com.emakarovas.animalrescue.testutil.TestUtils

import play.api.test.Helpers.await
import play.api.test.Helpers.defaultAwaitTimeout

class DefaultSearchDAOSpec extends DelayedPlaySpec with OneAppPerSuite {
  
  private val CommonUserId = "user id 1"
  
  private var search1 = SearchModel("id 1", "url 1", 
      List(
          SearchAnimalModel("search animal id 1", AnimalType.Pig, Some("pink pig"), Gender.Female, Some(12), Some(24),
              List("potential id 1", "potential id 2"),
              Some(SearchTerminationReasonModel(SearchTerminationReason.FoundHere, Some("text 1"))))),
      LocationModel("loc id 1", "country 1", "city 1", Some("street 1"), GeolocationModel(15.5, 16.5)),
      List(
          CommentModel("comment id 1", TestUtils.buildDate(3, 3, 2013), "comment text 1", Some("comment name 1"), 
          Some("comment user id 1")),
          CommentModel("comment id 2", TestUtils.buildDate(4, 4, 2014), "comment text 2", Some("comment name 2"), 
          Some("comment user id 2"))),
      TestUtils.buildDate(5, 5, 1975), Some(TestUtils.buildDate(6, 6, 1975)), true, CommonUserId)
  private var search2 = SearchModel("id 2", "url 2", 
      List(), 
      LocationModel("loc id 2", "country 2", "city 2", Some("street 2"), GeolocationModel(25.5, 26.5)),
      List(),
      TestUtils.buildDate(8, 8, 1988), None, false, "other user id")
  private var search3 = SearchModel("id 3", "url 3", 
      List(
          SearchAnimalModel("search animal id 3", AnimalType.Hedgehog, Some("cutehog"), Gender.Unspecified, Some(32), Some(44),
              List("potential id 3", "potential id 22"),
              Some(SearchTerminationReasonModel(SearchTerminationReason.FoundElsewhere, Some("text 3"))))),
      LocationModel("loc id 3", "country 3", "city 3", Some("street 3"), GeolocationModel(35.5, 36.5)),
      List(
          CommentModel("comment id 3", TestUtils.buildDate(3, 3, 2033), "comment text 3", Some("comment name 3"), 
          Some("comment user id 3")),
          CommentModel("comment id 5", TestUtils.buildDate(4, 4, 5034), "comment text 5", Some("comment name 5"), 
          Some("comment user id 5"))),
      TestUtils.buildDate(5, 5, 3975), Some(TestUtils.buildDate(6, 6, 3975)), true, CommonUserId)

  lazy val defaultSearchDAO = app.injector.instanceOf[DefaultSearchDAO]
    
  import scala.concurrent.ExecutionContext.Implicits.global

  "DefaultSearchDAOSpec" should {
    
    "create a new SearchModel by calling create()" in {
      val crF = defaultSearchDAO.create(search1)
      await(crF)
      crF onSuccess {
        case n => n mustBe 1
      }
      val findF = crF.flatMap(_ => defaultSearchDAO.findById(search1.id))
      await(findF)
      findF onSuccess {
        case op => op.get mustBe search1
      }
    }
    
    "create a new SearchModel by calling create(), even with some Nones" in {
      val crF = defaultSearchDAO.create(search2)
      await(crF)
      crF onSuccess {
        case n => n mustBe 1
      }
      val findF = crF.flatMap(_ => defaultSearchDAO.findById(search2.id))
      await(findF)
      findF onSuccess {
        case searchOpt => searchOpt.get mustBe search2
      }
    }
    
    "after calling create(), return the SearchModel in findById even before the save is done in the DB" in {
      val crF = defaultSearchDAO.create(search3)
      val findF = defaultSearchDAO.findById(search3.id)
      await(findF)
      findF onSuccess {
        case opt => {
          opt.isDefined mustBe true
          opt.get mustBe search3
        }
      }
    }
    
    "allow to update a SearchModel if no properties have been updated individually" in {
      delay()
      val findF = defaultSearchDAO.findUpdatableById(search1.id)
      await(findF)
      val updF = findF.flatMap(updateOpt => {
        updateOpt.isDefined mustBe true
        val search = updateOpt.get.model
        search mustBe search1
        search1 = search1.copy(url="updated url 1")
        defaultSearchDAO.update(VersionedModelContainer[SearchModel](search1, updateOpt.get.version))
      })
      await(updF)
      val updatedFindF = updF.flatMap(n => {
        n mustBe 1
        defaultSearchDAO.findById(search1.id)
      })
      await(updatedFindF)
      updatedFindF onSuccess {
        case opt => {
          opt.isDefined mustBe true
          opt.get mustBe search1
        }
      }
    }
    
    "update SearchUpdatableProperty's that are not None's" in {
      delay()
      
      search1 = search1.copy(
          isPublic = !search1.isPublic
      )
      
      val pubProp = SearchUpdatableProperty.IsPublic(search1.isPublic)
      val updF1 = defaultSearchDAO.updatePropertyById(search1.id, pubProp)
      await(updF1)
      
      val findF = updF1.flatMap(_ => defaultSearchDAO.findById(search1.id))
      await(findF)
      findF onSuccess {
        case op: Option[SearchModel] => {
          op.get mustBe search1
        }
      }

    }

    "deny update request when a property has been updated during model manipulation" in {
      delay()
      // find the model and lock it
      val findF = defaultSearchDAO.findUpdatableById(search1.id)
      await(findF)
      // change model and issue a property update
      search1 = search1.copy(isPublic = !search1.isPublic)
      defaultSearchDAO.updatePropertyById(search1.id, SearchUpdatableProperty.IsPublic(search1.isPublic))
      // try to update
      val modelUpdateF = findF.flatMap(
          op => {
            val token = op.get.version
            defaultSearchDAO.update(VersionedModelContainer[SearchModel](search2, token))
          }
      )
      await(modelUpdateF)
      modelUpdateF onSuccess {
        case n => n mustBe 0
      }

    }
    
    "insert SearchInsertableCollectionProperty's" in {
      delay()
      val newComment = CommentModel("comment id 155", TestUtils.buildDate(3, 3, 2018), "comment text 14", Some("comment name 12"), 
          Some("comment user id 15555"))
      val newSearchAnimal = SearchAnimalModel("search animal id 16", AnimalType.Pig, Some("pink pig"), 
          Gender.Female, Some(12), Some(24),
          List("potential id 1", "potential id 2"),
          Some(SearchTerminationReasonModel(SearchTerminationReason.FoundHere, Some("text 1"))))
      search1 = search1.copy(
          commentList = search1.commentList ::: List(newComment),
          searchAnimalList = search1.searchAnimalList ::: List(newSearchAnimal)
      )
      
      val comProp = SearchInsertableCollectionProperty.Comment(newComment)
      val updF1 = defaultSearchDAO.insertCollectionPropertyById(search1.id, comProp)
      
      val anProp = SearchInsertableCollectionProperty.SearchAnimal(newSearchAnimal)
      val updF2 = defaultSearchDAO.insertCollectionPropertyById(search1.id, anProp)
      
      val compF = for {
        f1 <- updF1
        f2 <- updF2
      } yield(f1 + f2)
      await(compF)
      
      val findF = compF.flatMap(_ => defaultSearchDAO.findById(search1.id))
      await(findF)
      findF onSuccess {
        case op => op.get mustBe search1
      }
    }
    
    "update SearchUpdatableCollectionProperty's that are not None's" in {
      delay()
      
      val oldAnimal = search1.searchAnimalList(0)
      val updatedSearchAnimal = oldAnimal.copy(
          specificType = Some("updated specific type of animal"),
          gender = Gender.Unspecified,
          minAge = Some(155),
          maxAge = Some(255)
      )
      val oldList = search1.searchAnimalList
      val newList = List(updatedSearchAnimal) ::: oldList.slice(1, oldList.size)
      
      search1 = search1.copy(
          searchAnimalList = newList
      )
      
      val specTypeProp = SearchUpdatableCollectionProperty.SearchAnimalListSpecificType(
          updatedSearchAnimal.id, updatedSearchAnimal.specificType)
      val updF1 = defaultSearchDAO.updateCollectionPropertyById(search1.id, specTypeProp)
      
      val genProp = SearchUpdatableCollectionProperty.SearchAnimalListGender(
          updatedSearchAnimal.id, updatedSearchAnimal.gender)
      val updF2 = defaultSearchDAO.updateCollectionPropertyById(search1.id, genProp)
      
      val minAgeProp = SearchUpdatableCollectionProperty.SearchAnimalListMinAge(
          updatedSearchAnimal.id, updatedSearchAnimal.minAge)
      val updF3 = defaultSearchDAO.updateCollectionPropertyById(search1.id, minAgeProp)
      
      val maxAgeProp = SearchUpdatableCollectionProperty.SearchAnimalListMaxAge(
          updatedSearchAnimal.id, updatedSearchAnimal.maxAge)
      val updF4 = defaultSearchDAO.updateCollectionPropertyById(search1.id, maxAgeProp)
      
      val compF = for {
        f1 <- updF1
        f2 <- updF2
        f3 <- updF3
        f4 <- updF4
      } yield(f1 + f2 + f3 + f4)
      await(compF)
      
      val findF = compF.flatMap(_ => defaultSearchDAO.findById(search1.id))
      await(findF)
      findF onSuccess {
        case op => op.get mustBe search1
      }
    }
    
    "update SearchUpdatableCollectionProperty's that are None's" in {
      delay()
      
      val oldAnimal = search1.searchAnimalList(0)
      val updatedSearchAnimal = oldAnimal.copy(
          specificType = None,
          minAge = None,
          maxAge = None
      )
      val oldList = search1.searchAnimalList
      val newList = List(updatedSearchAnimal) ::: oldList.slice(1, oldList.size)
      
      search1 = search1.copy(
          searchAnimalList = newList
      )
      
      val specTypeProp = SearchUpdatableCollectionProperty.SearchAnimalListSpecificType(
          updatedSearchAnimal.id, updatedSearchAnimal.specificType)
      val updF1 = defaultSearchDAO.updateCollectionPropertyById(search1.id, specTypeProp)
      
      val minAgeProp = SearchUpdatableCollectionProperty.SearchAnimalListMinAge(
          updatedSearchAnimal.id, updatedSearchAnimal.minAge)
      val updF2 = defaultSearchDAO.updateCollectionPropertyById(search1.id, minAgeProp)
      
      val maxAgeProp = SearchUpdatableCollectionProperty.SearchAnimalListMaxAge(
          updatedSearchAnimal.id, updatedSearchAnimal.maxAge)
      val updF3 = defaultSearchDAO.updateCollectionPropertyById(search1.id, maxAgeProp)
      
      val compF = for {
        f1 <- updF1
        f2 <- updF2
        f3 <- updF3
      } yield(f1 + f2 + f3)
      await(compF)
      
      val findF = compF.flatMap(_ => defaultSearchDAO.findById(search1.id))
      await(findF)
      findF onSuccess {
        case op => op.get mustBe search1
      }
    }
    
    "delete SearchDeletableProperty's" in {
      delay()
      
      val deletedAnimalId = search1.searchAnimalList(0).id
      search1 = search1.copy(
          searchAnimalList = search1.searchAnimalList.slice(1, search1.searchAnimalList.size)
      )
      
      val prop = SearchDeletableCollectionProperty.SearchAnimal(deletedAnimalId)
      val updF = defaultSearchDAO.deleteCollectionPropertyById(search1.id, prop)
      await(updF)
      val findF = updF.flatMap(_ => defaultSearchDAO.findById(search1.id))
      await(findF)
      findF onSuccess {
        case op => op.get mustBe search1
      }
    }
    
    "add to potentialAnimalIdList when addToPotentialAnimalIdListBySearchAnimalId is called if it doesn't exist yet" in {
      delay()
      val addedId = "added id"
      val oldAnimal = search1.searchAnimalList(0)
      val updatedAnimal = oldAnimal.copy(
          potentialAnimalIdList = oldAnimal.potentialAnimalIdList ::: List(addedId)
      )
      val oldList = search1.searchAnimalList
      val updatedList = List(updatedAnimal) ::: oldList.slice(1, oldList.size)
      search1 = search1.copy(
          searchAnimalList = updatedList
      )
      val addF = defaultSearchDAO.addToPotentialAnimalIdListBySearchAnimalId(search1.id, updatedAnimal.id, addedId)
      await(addF)
      val findF = addF.flatMap(n => {
        n mustBe 1 
        defaultSearchDAO.findById(search1.id)
      })
      await(findF)
      val addF2 = findF.flatMap(op => {
        op.get mustBe search1
        defaultSearchDAO.addToPotentialAnimalIdListBySearchAnimalId(search1.id, updatedAnimal.id, addedId)
      })
      await(addF2)
      val findF2 = addF2.flatMap(n => {
        n mustBe 0
        defaultSearchDAO.findById(search1.id)
      })
      await(findF2)
      findF2 onSuccess {
        // the retrieved search must be the same, i.e. have the same list length
        case op => op.get mustBe search1
      }
    }
    
    "find a list of all SearchModels when findAll() is called" in {
      delay()
      val findF = defaultSearchDAO.findAll()
      await(findF)
      findF onSuccess {
        case list => {
          list.size mustBe 3
          list.contains(search1) mustBe true
          list.contains(search2) mustBe true
          list.contains(search3) mustBe true
        }
      }
    }
    
    "find SearchModels when findByUserId() is called" in {
      delay()
      val findF = defaultSearchDAO.findByUserId(CommonUserId)
      await(findF)
      findF onSuccess {
        case list => {
          list.size mustBe 2
          list.contains(search1) mustBe true
          list.contains(search3) mustBe true
        }
      }
    }
    
    "delete SearchModels when deleteById() is called" in {
      delay()
      val singleDeleteF = defaultSearchDAO.deleteById(search1.id)
      await(singleDeleteF)
      val findF = singleDeleteF.flatMap(_ => defaultSearchDAO.findAll())
      await(findF)
      findF onSuccess {
        case list => {
          list.size mustBe 2
          list.contains(search2) mustBe true
          list.contains(search3) mustBe true
        }
      }
      val delF2 = findF.flatMap(_ => defaultSearchDAO.deleteById(search2.id))
      val delF3 = findF.flatMap(_ => defaultSearchDAO.deleteById(search3.id))
      val allDelF = for {
        f2 <- delF2
        f3 <- delF3
      } yield(f2 + f3)
      await(allDelF)
      val findF2 = allDelF.flatMap(_ => defaultSearchDAO.findAll())
      await(findF2)
      findF2 onSuccess {
        case list => list.size mustBe 0
      }
    }
    
  }
  
}