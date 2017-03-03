package com.emakarovas.animalrescue.util.impl

import org.scalatestplus.play.OneAppPerSuite
import org.scalatestplus.play.PlaySpec

import com.emakarovas.animalrescue.util.generator.impl.DefaultSaltGenerator

class DefaultSaltGeneratorSpec extends PlaySpec with OneAppPerSuite {
  
  lazy val defaultISaltGenerator: DefaultSaltGenerator = app.injector.instanceOf[DefaultSaltGenerator]
  
  "DefaultSaltGenerator" should {
    
    "generate strings of length 20" in {
      
      for(i <- 0 to 100) {
        val salt = defaultISaltGenerator.generate
        salt.length mustBe 20
      }
    }
  }

}