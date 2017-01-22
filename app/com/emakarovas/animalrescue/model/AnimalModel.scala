package com.emakarovas.animalrescue.model

import com.emakarovas.animalrescue.model.animal.AnimalType
import com.emakarovas.animalrescue.model.common.Gender

case class AnimalModel
  (override val id: String,
   animalType: AnimalType,
   specificType: Option[String],
   name: Option[String],
   gender: Gender,
   age: Option[Int], // this is stored as months
   description: Option[String])
   extends AbstractModel(id) {
  
}