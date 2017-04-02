package com.emakarovas.animalrescue.model.enumeration

import com.emakarovas.animalrescue.model.AbstractModel
import com.emakarovas.animalrescue.model.AnimalModel
import com.emakarovas.animalrescue.model.CommentModel
import com.emakarovas.animalrescue.model.ImageModel
import com.emakarovas.animalrescue.model.Location
import com.emakarovas.animalrescue.model.OfferModel
import com.emakarovas.animalrescue.model.PersonModel
import com.emakarovas.animalrescue.model.SearchAnimalModel
import com.emakarovas.animalrescue.model.SearchModel
import com.emakarovas.animalrescue.model.UserModel

sealed trait ModelType[+T <: AbstractModel] extends Enum[ModelType[AbstractModel]]

object ModelType extends Enum[ModelType[AbstractModel]] {
  case object Animal extends ModelType[AnimalModel]
  case object Comment extends ModelType[CommentModel]
  case object Image extends ModelType[ImageModel]
  case object Offer extends ModelType[OfferModel]
  case object Person extends ModelType[PersonModel]
  case object Search extends ModelType[SearchModel]
  case object SearchAnimal extends ModelType[SearchAnimalModel]
  case object User extends ModelType[UserModel]
  override val values: List[ModelType[AbstractModel]] = List(Animal, Comment, Image, Offer, Person, Search, SearchAnimal, User)
  
  def valueOf(value: String): ModelType[AbstractModel] = value match {
    case "Animal" => Animal
    case "Comment" => Comment
    case "Image" => Image
    case "Offer" => Offer
    case "Person" => Person
    case "Search" => Search
    case "SearchAnimal" => SearchAnimal
    case "User" => User
    case _ => throw new EnumerationNotFoundException()
  }
  
}
  