package com.emakarovas.animalrescue.model

import com.emakarovas.animalrescue.model.enumeration.Gender

case class UserModel
  (override val id: String,
   email: String,
   hashedPassword: String,
   salt: Option[String],
   activationString: Option[String],
   passwordResetString: Option[String],
   person: Option[PersonModel],
   settings: AccountSettingsModel) extends AbstractModel(id) with AbstractPersistableEntity

case class PersonModel
  (override val id: String,
   name: String, 
   surname: Option[String],
   gender: Gender,
   phoneNumber: Option[String],
   image: Option[ImageModel],
   location: LocationModel) 
   extends AbstractModel(id)

case class AccountSettingsModel
  (sendEmailsWithMatches: Boolean // if user has posted a search or offer
   ) extends AbstractEntity