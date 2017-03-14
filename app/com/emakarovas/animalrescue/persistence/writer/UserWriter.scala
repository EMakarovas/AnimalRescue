package com.emakarovas.animalrescue.persistence.writer

import com.emakarovas.animalrescue.model.AccountSettingsModel
import com.emakarovas.animalrescue.model.PersonModel
import com.emakarovas.animalrescue.model.UserModel
import com.emakarovas.animalrescue.model.constants.AccountSettingsConstants
import com.emakarovas.animalrescue.model.constants.PersonConstants
import com.emakarovas.animalrescue.model.constants.UserConstants
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants
import com.emakarovas.animalrescue.persistence.writer.enumeration.GenderWriter

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class UserWriter @Inject() (
    implicit private val genderWriter: GenderWriter,
    implicit private val imageWriter: ImageWriter,
    implicit private val locationWriter: LocationWriter) extends AbstractEntityWriter[UserModel] {
  
  implicit object personWriter extends AbstractEntityWriter[PersonModel] {
    override def write(person: PersonModel): BSONDocument = {
      BSONDocument(
        MongoConstants.MongoId -> person.id,
        PersonConstants.Name -> person.name,
        PersonConstants.Surname -> person.surname,
        PersonConstants.Gender -> person.gender,
        PersonConstants.PhoneNumber -> person.phoneNumber,
        PersonConstants.Image -> person.image,
        PersonConstants.Location -> person.location)
    }
  }
  
  implicit object accountSettingsWriter extends AbstractEntityWriter[AccountSettingsModel] {
    override def write(accountSettings: AccountSettingsModel): BSONDocument = {
      BSONDocument(
          AccountSettingsConstants.SendEmailsWithMatches -> accountSettings.sendEmailsWithMatches)
    }
  }
  
  override def write(user: UserModel): BSONDocument = {
    BSONDocument(
        MongoConstants.MongoId -> user.id,
        MongoConstants.Data -> BSONDocument(
            UserConstants.Email -> user.email,
            UserConstants.HashedPassword -> user.hashedPassword,
            UserConstants.Salt -> user.salt,
            UserConstants.ActivationString -> user.activationString,
            UserConstants.PasswordResetString -> user.passwordResetString,
            UserConstants.Person -> user.person,
            UserConstants.Settings -> user.settings))
  }
  
}