package com.emakarovas.animalrescue.persistence.dao.impl

import org.scalatestplus.play.OneAppPerTest
import org.scalatestplus.play.PlaySpec
import play.api.test.Helpers
import org.scalatestplus.play._
import play.api.test._
import play.api.test.Helpers._

import com.emakarovas.animalrescue.model.PersonModel
import javax.inject.Inject
import scala.util.Failure
import scala.util.Success

class DefaultPersonDAOSpec extends PlaySpec with OneAppPerTest {
  
  import scala.concurrent.ExecutionContext.Implicits.global
  
  "DefaultPersonDAO..." should {
    "store a new PersonModel in the DB when create is called" in {
      val defaultPersonDAO: DefaultPersonDAO = app.injector.instanceOf[DefaultPersonDAO]
      val person = new PersonModel("test id", "test name", "test surname")
      val f = defaultPersonDAO.create(person)
      f onComplete {
        case Success(writeRes) => {
          println(writeRes)
          writeRes.n mustBe 1 }
        case Failure(t) => println(t)
      }
      Thread.sleep(10000)
    }
  }
  
}