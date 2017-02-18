package com.emakarovas.animalrescue.model

import com.emakarovas.animalrescue.model.enumeration.Gender

case class PersonModel
  (override val id: String,
   name: String, 
   surname: Option[String],
   gender: Gender,
   image: Option[ImageModel],
   userId: String) 
   extends AbstractModel(id) {
  
}