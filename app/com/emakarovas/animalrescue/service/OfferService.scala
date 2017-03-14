package com.emakarovas.animalrescue.service

import scala.concurrent.Future

import com.emakarovas.animalrescue.model.ImageModel
import com.emakarovas.animalrescue.model.LocationModel
import com.emakarovas.animalrescue.model.OfferModel
import com.emakarovas.animalrescue.model.VideoModel

trait OfferService {
  def createOffer(
      text: String, 
      image: Option[ImageModel], 
      video: Option[VideoModel], 
      location: LocationModel, 
      userId: String): Future[Unit]
  def findByUserId(userId: String): Future[List[OfferModel]]
}