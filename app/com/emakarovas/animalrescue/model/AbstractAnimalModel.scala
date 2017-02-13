package com.emakarovas.animalrescue.model

import com.emakarovas.animalrescue.model.enumeration.AnimalType
import com.emakarovas.animalrescue.model.enumeration.Gender

abstract class AbstractAnimalModel
  (override val id: String,
   animalType: AnimalType,
   specificType: Option[String],
   name: Option[String],
   gender: Gender,
   age: Option[Int], // stored as months
   description: Option[String],
   image: Option[ImageModel])
   extends AbstractModel(id)