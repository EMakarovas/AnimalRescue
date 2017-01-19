package com.emakarovas.animalrescue.model

case class PersonModel
  (id: String,
   name: String, 
   surname: String) 
   extends AbstractModel(id) {
  
}