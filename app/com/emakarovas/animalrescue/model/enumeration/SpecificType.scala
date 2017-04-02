package com.emakarovas.animalrescue.model.enumeration

import scala.reflect.runtime.universe._

sealed trait SpecificType[+T <: Animal] extends Enum[SpecificType[Animal]] {
  def getAsAnimal: TypeTag[_ <: Animal]
}

sealed trait BirdSpecificType extends SpecificType[Animal.Bird] {
  override def getAsAnimal = typeTag[Animal.Bird]
}

sealed trait CatSpecificType extends SpecificType[Animal.Cat] {
  override def getAsAnimal = typeTag[Animal.Cat]
}

sealed trait ChinchillaSpecificType extends SpecificType[Animal.Chinchilla] {
  override def getAsAnimal = typeTag[Animal.Chinchilla]
}

sealed trait DogSpecificType extends SpecificType[Animal.Dog] {
  override def getAsAnimal = typeTag[Animal.Dog]
}

sealed trait FerretSpecificType extends SpecificType[Animal.Ferret] {
  override def getAsAnimal = typeTag[Animal.Ferret]
}

sealed trait GuineaPigSpecificType extends SpecificType[Animal.GuineaPig] {
  override def getAsAnimal = typeTag[Animal.GuineaPig]
}

sealed trait HamsterSpecificType extends SpecificType[Animal.Hamster] {
  override def getAsAnimal = typeTag[Animal.Hamster]
}

sealed trait HedgehogSpecificType extends SpecificType[Animal.Hedgehog] {
  override def getAsAnimal = typeTag[Animal.Hedgehog]
}

sealed trait MouseSpecificType extends SpecificType[Animal.Mouse] {
  override def getAsAnimal = typeTag[Animal.Mouse]
}

sealed trait PigSpecificType extends SpecificType[Animal.Pig] {
  override def getAsAnimal = typeTag[Animal.Pig]
}

sealed trait RabbitSpecificType extends SpecificType[Animal.Rabbit] {
  override def getAsAnimal = typeTag[Animal.Rabbit]
}

sealed trait RatSpecificType extends SpecificType[Animal.Rat] {
  override def getAsAnimal = typeTag[Animal.Rat]
}

sealed trait SnakeSpecificType extends SpecificType[Animal.Snake] {
  override def getAsAnimal = typeTag[Animal.Snake]
}

object SpecificType extends Enum[SpecificType[_]] {
  
  // Bird
  case object Parrot extends BirdSpecificType
  
  // Cat
  case object CommonEuropean extends CatSpecificType
    
  // Dog
  case object CockerSpaniel extends DogSpecificType
  case object GoldenRetriever extends DogSpecificType
  case object Poodle extends DogSpecificType

  // Any
  case object Unspecified extends SpecificType[Animal] {
    override def getAsAnimal = typeTag[Animal]
  }
  
  def valueOf(value: String): SpecificType[Animal] = value match {
    case "Parrot" => Parrot
    case "CommonEuropean" => CommonEuropean
    case "CockerSpaniel" => CockerSpaniel
    case "GoldenRetriever" => GoldenRetriever
    case "Poodle" => Poodle
    case "Unspecified" => Unspecified
    case _ => throw new EnumerationNotFoundException()
  }
  
}