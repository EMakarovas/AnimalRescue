package com.emakarovas.animalrescue.model.property

import com.emakarovas.animalrescue.model.OfferModel
import com.emakarovas.animalrescue.model.ImageModel
import com.emakarovas.animalrescue.model.VideoModel
import com.emakarovas.animalrescue.model.constants.OfferConstants

sealed abstract class OfferUpdatableProperty[T](
    override val name: String, override val value: T) extends UpdatableProperty[OfferModel, T] with OfferProperty
    
object OfferUpdatableProperty {
  case class OfferTextProperty(override val value: String) 
    extends OfferUpdatableProperty[String](OfferConstants.Text, value)
  case class OfferImageProperty(override val value: Option[ImageModel]) 
    extends OfferUpdatableProperty[Option[ImageModel]](OfferConstants.Image, value)
  case class OfferVideoProperty(override val value: Option[VideoModel]) 
    extends OfferUpdatableProperty[Option[VideoModel]](OfferConstants.Video, value)
}