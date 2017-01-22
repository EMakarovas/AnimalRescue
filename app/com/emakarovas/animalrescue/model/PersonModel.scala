package com.emakarovas.animalrescue.model

case class PersonModel
  (override val id: String,
   name: String, 
   surname: String) 
   extends AbstractModel(id) {
  
}