package com.emakarovas.animalrescue.model.constants

import com.emakarovas.animalrescue.model.enumeration.Gender

object UserConstants {
  val Id = "id"
  val Email = "email"
  val HashedPassword = "hashedPassword"
  val Salt = "salt"
  val ActivationString = "activationString"
  val PasswordResetString = "passwordResetString"
  val Person = "person"
  val Settings = "settings"
}

object PersonConstants {
  val Id = "id"
  val Name = "name"
  val Surname = "surname"
  val Gender = "gender"
  val PhoneNumber = "phoneNumber"
  val Image = "image"
  val Location = "location"
}

object AccountSettingsConstants {
  val SendEmailsWithMatches = "sendEmailsWithMatches"
}