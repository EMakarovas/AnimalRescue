package com.emakarovas.animalrescue.model.property.unique

import com.emakarovas.animalrescue.model.UserModel
import com.emakarovas.animalrescue.model.constants.UserConstants

sealed abstract class UserUniqueProperty[T](
   override val propertyName: String, override val propertyValue: T) 
   extends UniqueProperty[UserModel, T](propertyName, propertyValue)

object UserUniqueProperty {
  case class EmailProperty(override val propertyValue: String) extends UserUniqueProperty[String](UserConstants.Email, propertyValue)
}