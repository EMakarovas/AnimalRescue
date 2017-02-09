package com.emakarovas.animalrescue.persistence.writer

import com.emakarovas.animalrescue.model.UserModel

import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class UserWriter extends AbstractModelWriter[UserModel] {
  override def write(user: UserModel): BSONDocument = {
    BSONDocument(
        "_id" -> user.id,
        "email" -> user.email,
        "hashedPassword" -> user.hashedPassword,
        "salt" -> user.salt)
  }
}