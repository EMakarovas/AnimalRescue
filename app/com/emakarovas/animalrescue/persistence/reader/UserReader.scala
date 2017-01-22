package com.emakarovas.animalrescue.persistence.reader

import java.util.Date

import com.emakarovas.animalrescue.model.UserModel

import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class UserReader extends AbstractModelReader[UserModel] {
  def read(doc: BSONDocument): UserModel = {
    val id = doc.getAs[String]("_id").get
    val email = doc.getAs[String]("email").get
    val hashedPassword = doc.getAs[String]("hashedPassword").get
    val salt = doc.getAs[String]("salt").get
    UserModel(id, email, hashedPassword, salt)
  }
}