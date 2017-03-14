package com.emakarovas.animalrescue.persistence.reader

import com.emakarovas.animalrescue.model.AccountSettingsModel
import com.emakarovas.animalrescue.model.ImageModel
import com.emakarovas.animalrescue.model.LocationModel
import com.emakarovas.animalrescue.model.PersonModel
import com.emakarovas.animalrescue.model.UserModel
import com.emakarovas.animalrescue.model.constants.AccountSettingsConstants
import com.emakarovas.animalrescue.model.constants.PersonConstants
import com.emakarovas.animalrescue.model.constants.UserConstants
import com.emakarovas.animalrescue.model.enumeration.Gender
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants
import com.emakarovas.animalrescue.persistence.reader.enumeration.GenderReader

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.bson.BSONDocument
import reactivemongo.bson.BSONDocumentReader

@Singleton
class UserReader @Inject() (
    implicit val genderReader: GenderReader,
    implicit val imageReader: ImageReader,
    implicit val locationReader: LocationReader) extends AbstractEntityReader[UserModel] {
  
  implicit object personReader extends BSONDocumentReader[PersonModel] {
    override def read(doc: BSONDocument): PersonModel = {
      val id = doc.getAs[String](MongoConstants.MongoId).get
      val name = doc.getAs[String](PersonConstants.Name).get
      val surnameOpt = doc.getAs[String](PersonConstants.Surname)
      val gender = doc.getAs[Gender](PersonConstants.Gender).get
      val phoneNumberOpt = doc.getAs[String](PersonConstants.PhoneNumber)
      val imageOpt = doc.getAs[ImageModel](PersonConstants.Image)
      val location = doc.getAs[LocationModel](PersonConstants.Location).get
      PersonModel(id, name, surnameOpt, gender, phoneNumberOpt, imageOpt, location)
    }
  }
  
  implicit object accountSettingsReader extends BSONDocumentReader[AccountSettingsModel] {
    override def read(doc: BSONDocument): AccountSettingsModel = {
      val sendEmailsWithMatches = doc.getAs[Boolean](AccountSettingsConstants.SendEmailsWithMatches).get
      AccountSettingsModel(sendEmailsWithMatches)
    }
  }
  
  override def read(topDoc: BSONDocument): UserModel = {
    val id = topDoc.getAs[String](MongoConstants.MongoId).get
    val doc = topDoc.getAs[BSONDocument](MongoConstants.Data).get
    val email = doc.getAs[String](UserConstants.Email).get
    val hashedPassword = doc.getAs[String](UserConstants.HashedPassword).get
    val saltOpt = doc.getAs[String](UserConstants.Salt)
    val activationStringOpt = doc.getAs[String](UserConstants.ActivationString)
    val passwordResetStringOpt = doc.getAs[String](UserConstants.PasswordResetString)
    val personOpt = doc.getAs[PersonModel](UserConstants.Person)
    val settings = doc.getAs[AccountSettingsModel](UserConstants.Settings).get
    UserModel(id, email, hashedPassword, saltOpt, activationStringOpt, passwordResetStringOpt, personOpt, settings)
  }
  
}