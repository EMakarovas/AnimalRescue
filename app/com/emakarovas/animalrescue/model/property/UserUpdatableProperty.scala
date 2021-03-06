package com.emakarovas.animalrescue.model.property

import com.emakarovas.animalrescue.model.ImageModel
import com.emakarovas.animalrescue.model.UserModel
import com.emakarovas.animalrescue.model.constants.PersonConstants
import com.emakarovas.animalrescue.model.constants.UserConstants
import com.emakarovas.animalrescue.model.enumeration.Gender
import com.emakarovas.animalrescue.model.constants.AccountSettingsConstants

sealed abstract class UserUpdatableProperty[T](
    override val name: String, override val value: T) extends UpdatableProperty[UserModel, T] with UserProperty
    
object UserUpdatableProperty {
  case class PersonName(override val value: String) 
    extends UserUpdatableProperty[String](UserConstants.Person + "." + PersonConstants.Name, value)
  case class PersonSurname(override val value: Option[String]) 
    extends UserUpdatableProperty[Option[String]](UserConstants.Person + "." + PersonConstants.Surname, value)
  case class PersonGender(override val value: Gender)
    extends UserUpdatableProperty[Gender](UserConstants.Person + "." + PersonConstants.Gender, value)
  case class PersonPhoneNumber(override val value: Option[String])
    extends UserUpdatableProperty[Option[String]](UserConstants.Person + "." + PersonConstants.PhoneNumber, value)
  case class PersonImage(override val value: Option[ImageModel])
    extends UserUpdatableProperty[Option[ImageModel]](UserConstants.Person + "." + PersonConstants.Image, value)
  case class SettingsSendEmailsWithMatches(override val value: Boolean)
    extends UserUpdatableProperty[Boolean](UserConstants.Settings + "." + AccountSettingsConstants.SendEmailsWithMatches, value)
}