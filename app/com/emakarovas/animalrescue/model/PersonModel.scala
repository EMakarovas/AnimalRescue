package com.emakarovas.animalrescue.model

import com.emakarovas.animalrescue.model.common.Gender

case class PersonModel
  (override val id: String,
   name: String, 
   surname: String,
   gender: Gender.Value) 
   extends AbstractModel(id) {
  
}