package com.emakarovas.animalrescue.model.property

import com.emakarovas.animalrescue.model.AnimalModel
import com.emakarovas.animalrescue.model.ImageModel
import com.emakarovas.animalrescue.model.VideoModel
import com.emakarovas.animalrescue.model.constants.AnimalConstants

sealed abstract class AnimalUpdatableProperty[T](
    override val name: String, override val value: T) extends UpdatableProperty[AnimalModel, T] with AnimalProperty

object AnimalUpdatableProperty {
  
  case class Gender(override val value: Option[com.emakarovas.animalrescue.model.enumeration.Gender]) 
    extends AnimalUpdatableProperty[Option[com.emakarovas.animalrescue.model.enumeration.Gender]](AnimalConstants.Gender, value)
    
  case class Name(override val value: Option[String]) 
    extends AnimalUpdatableProperty[Option[String]](AnimalConstants.Name, value)
    
  case class Age(override val value: Int) 
    extends AnimalUpdatableProperty[Int](AnimalConstants.Age, value)
    
  case class Description(override val value: Option[String]) 
    extends AnimalUpdatableProperty[Option[String]](AnimalConstants.Description, value)
    
  case class Size(override val value: com.emakarovas.animalrescue.model.enumeration.Size)
    extends AnimalUpdatableProperty[com.emakarovas.animalrescue.model.enumeration.Size](AnimalConstants.Size, value)
    
  case class IsCastrated(override val value: Boolean)
    extends AnimalUpdatableProperty[Boolean](AnimalConstants.IsCastrated, value)
    
  case class Image(override val value: Option[ImageModel]) 
    extends AnimalUpdatableProperty[Option[ImageModel]](AnimalConstants.Image, value)
    
  case class Video(override val value: Option[VideoModel]) 
    extends AnimalUpdatableProperty[Option[VideoModel]](AnimalConstants.Video, value)
    
}