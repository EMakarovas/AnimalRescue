package com.emakarovas.animalrescue.service

import org.mindrot.jbcrypt.BCrypt
import com.google.inject.ImplementedBy
import com.emakarovas.animalrescue.service.impl.DefaultPasswordService

@ImplementedBy(classOf[DefaultPasswordService])
trait PasswordService {
  def hashPassword(psw: String): String
  def checkPassword(psw: String, hashedPsw: String): Boolean
}