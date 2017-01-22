package com.emakarovas.animalrescue.model

case class UserModel
  (override val id: String,
   email: String,
   hashedPassword: String,
   salt: String) extends AbstractModel(id) {
  
}