package com.emakarovas.animalrescue.util.impl

import org.scalatestplus.play.OneAppPerSuite
import org.scalatestplus.play.PlaySpec

import com.emakarovas.animalrescue.util.generator.impl.DefaultStringGenerator

class DefaultStringGeneratorSpec extends PlaySpec with OneAppPerSuite {
  
  lazy val defaultStringGenerator: DefaultStringGenerator = app.injector.instanceOf[DefaultStringGenerator]
  
  "DefaultStringGenerator" should {
    
    "generate strings of given length" in {
      
      for(i <- 0 to 100) {
        val salt = defaultStringGenerator.generate(i)
        salt.length mustBe i
      }
    }
  }

}