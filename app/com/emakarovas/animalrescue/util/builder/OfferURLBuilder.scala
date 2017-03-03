package com.emakarovas.animalrescue.util.builder

import com.emakarovas.animalrescue.model.AnimalModel
import com.emakarovas.animalrescue.model.OfferModel
import com.google.inject.ImplementedBy
import com.emakarovas.animalrescue.util.builder.impl.DefaultOfferURLBuilder

@ImplementedBy(classOf[DefaultOfferURLBuilder])
trait OfferURLBuilder extends AbstractURLBuilder[OfferModel] {
  def build(offer: OfferModel, firstAnimal: AnimalModel): String
}