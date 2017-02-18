package com.emakarovas.animalrescue.model

case class UserModel
  (override val id: String,
   email: String,
   hashedPassword: String,
   salt: String,
   available: Boolean) extends AbstractModel(id) with AvailableModel[UserModel] {
  
  override def buildAvailable(): UserModel  = {
    this.copy(available=true)
  }
 
}