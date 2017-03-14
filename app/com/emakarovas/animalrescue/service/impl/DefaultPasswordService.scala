package com.emakarovas.animalrescue.service.impl

import org.mindrot.jbcrypt.BCrypt

import com.emakarovas.animalrescue.service.PasswordService

import javax.inject.Singleton

@Singleton
class DefaultPasswordService extends PasswordService {
  
  override def hashPassword(psw: String): String = {
    BCrypt.hashpw(psw, BCrypt.gensalt())
  }
  def checkPassword(psw: String, hashedPsw: String): Boolean = {
    BCrypt.checkpw(psw, hashedPsw)
  }
  
}