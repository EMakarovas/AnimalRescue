package com.emakarovas.animalrescue.model

case class UserModel
  (id: String,
   email: String,
   hashedPassword: String,
   salt: String) extends AbstractModel(id) {
  
}