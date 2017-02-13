package com.emakarovas.animalrescue.service

import com.emakarovas.animalrescue.form.RegistrationForm
import scala.concurrent.Future

trait UserService {
  def handleRegistration(registrationForm: RegistrationForm) // TODO investigate what to return here
}