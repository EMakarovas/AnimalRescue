package com.emakarovas.animalrescue.util.impl

import org.scalatestplus.play.OneAppPerSuite
import org.scalatestplus.play.PlaySpec

class DefaultIdGeneratorSpec extends PlaySpec with OneAppPerSuite {
  
  lazy val defaultIdGenerator: DefaultIdGenerator = app.injector.instanceOf[DefaultIdGenerator]
  val buffer = scala.collection.mutable.ArrayBuffer.empty[String]
  
  "DefaultIdGenerator" should {
    
    "generate completely random IDs" in {
      for(i <- 0 to 100) {
        val str = defaultIdGenerator.generate
        buffer.contains(str) mustBe false
        buffer += defaultIdGenerator.generate
      }
    }
    
    "generate IDs of length 36" in {
      for(id <- buffer)
        id.length mustBe 36
    }
    
  }

}