package com.emakarovas.animalrescue.service

import scala.concurrent.Future

import com.emakarovas.animalrescue.model.AdoptionDetailsModel
import com.emakarovas.animalrescue.model.AnimalModel
import com.emakarovas.animalrescue.model.ImageModel
import com.emakarovas.animalrescue.model.VideoModel
import com.emakarovas.animalrescue.model.enumeration.AnimalType
import com.emakarovas.animalrescue.model.enumeration.Gender

trait AnimalService {
  def createAnimal(
      animalType: AnimalType,
      specificType: Option[String],
      gender: Gender,
      name: Option[String],
      age: Option[Int],
      description: Option[String],
      isCastrated: Boolean,
      image: Option[ImageModel],
      video: Option[VideoModel],
      adoptionDetails: Option[AdoptionDetailsModel]): Future[Unit]
      
  def findByOwnerId(ownerId: String): Future[List[AnimalModel]]
  def findByOfferId(offerId: String): Future[List[AnimalModel]]
}