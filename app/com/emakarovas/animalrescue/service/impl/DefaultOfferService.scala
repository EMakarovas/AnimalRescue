package com.emakarovas.animalrescue.service.impl

import scala.concurrent.Future

import com.emakarovas.animalrescue.model.ImageModel
import com.emakarovas.animalrescue.model.LocationModel
import com.emakarovas.animalrescue.model.OfferModel
import com.emakarovas.animalrescue.model.VideoModel
import com.emakarovas.animalrescue.model.enumeration.ModelType
import com.emakarovas.animalrescue.persistence.dao.CollectionCounterDAO
import com.emakarovas.animalrescue.persistence.dao.OfferDAO
import com.emakarovas.animalrescue.service.OfferService

import javax.inject.Inject
import com.emakarovas.animalrescue.util.builder.OfferURLBuilder

//@Singleton
abstract class DefaultOfferService @Inject() (
    val offerDAO: OfferDAO,
    val collectionCounterDAO: CollectionCounterDAO,
    val offerURLBuilder: OfferURLBuilder) extends OfferService {
  
//  override def createOffer(
//      text: String, 
//      image: Option[ImageModel], 
//      video: Option[VideoModel], 
//      location: LocationModel, 
//      userId: String): Future[Unit] = {
//    val id = collectionCounterDAO.get(ModelType.Offer)
//    val URL = offerURLBuilder. // this shouldn't take an offermodel, neither should take a first animal
//  }
//  
//  override def findByUserId(userId: String): Future[List[OfferModel]]
  
}