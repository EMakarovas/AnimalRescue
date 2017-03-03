package com.emakarovas.animalrescue.util.builder.impl

import com.emakarovas.animalrescue.util.builder.OfferURLBuilder

import javax.inject.Singleton
import com.emakarovas.animalrescue.model.OfferModel
import com.emakarovas.animalrescue.model.AnimalModel

@Singleton
class DefaultOfferURLBuilder extends OfferURLBuilder {
  override def build(offer: OfferModel, firstAnimal: AnimalModel): String = {
    val id = offer.id
    val animalType = firstAnimal.animalType
    val specificType = firstAnimal.specificType
    s"$id-$animalType-$specificType"
  }
  
}