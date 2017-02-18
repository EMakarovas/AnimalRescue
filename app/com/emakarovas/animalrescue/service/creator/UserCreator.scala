package com.emakarovas.animalrescue.service.creator

import com.emakarovas.animalrescue.model.UserModel
import com.emakarovas.animalrescue.model.PersonModel
import scala.concurrent.Future

trait UserCreator extends AbstractAvailableModelCreator[UserModel] {
  def create(user: UserModel, person: PersonModel): Future[Unit]
}