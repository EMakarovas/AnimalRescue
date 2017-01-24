package com.emakarovas.animalrescue.model

import com.emakarovas.animalrescue.model.enumeration.Gender

case class PersonModel
  (override val id: String,
   name: String, 
   surname: String,
   gender: Gender) 
   extends AbstractModel(id) {
  
}