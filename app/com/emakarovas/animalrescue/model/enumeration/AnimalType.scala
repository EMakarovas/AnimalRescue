package com.emakarovas.animalrescue.model.enumeration

import scala.reflect.runtime.universe._

sealed trait AnimalType[+T <: Animal] extends Enum[AnimalType[Animal]]

object AnimalType extends Enum[AnimalType[_]] {
  case object Bird extends AnimalType[Animal.Bird]
  case object Cat extends AnimalType[Animal.Cat]
  case object Chinchilla extends AnimalType[Animal.Chinchilla]
  case object Dog extends AnimalType[Animal.Dog]
  case object Ferret extends AnimalType[Animal.Ferret]
  case object GuineaPig extends AnimalType[Animal.GuineaPig]
  case object Hamster extends AnimalType[Animal.Hamster]
  case object Hedgehog extends AnimalType[Animal.Hedgehog]
  case object Mouse extends AnimalType[Animal.Mouse]
  case object Pig extends AnimalType[Animal.Pig]
  case object Rabbit extends AnimalType[Animal.Rabbit]
  case object Rat extends AnimalType[Animal.Rat]
  case object Snake extends AnimalType[Animal.Snake]
  
  def valueOf(value: String) = value match {
    case "Bird" => Bird
    case "Cat" => Cat
    case "Chinchilla" => Chinchilla
    case "Dog" => Dog
    case "Ferret" => Ferret
    case "GuineaPig" => GuineaPig
    case "Hamster" => Hamster
    case "Hedgehog" => Hedgehog
    case "Mouse" => Mouse
    case "Pig" => Pig
    case "Rabbit" => Rabbit
    case "Rat" => Rat
    case "Snake" => Snake
    case _ => throw new EnumerationNotFoundException()
  }
  
  def valueOf[T <: Animal : TypeTag](animalType: TypeTag[T]): AnimalType[Animal] = {
    animalType.tpe match {
      case t if  t =:= typeOf[Animal.Bird] => AnimalType.Bird
      case t if  t =:= typeOf[Animal.Cat] => AnimalType.Cat
      case t if  t =:= typeOf[Animal.Chinchilla] => AnimalType.Chinchilla
      case t if  t =:= typeOf[Animal.Dog] => AnimalType.Dog
      case t if  t =:= typeOf[Animal.Ferret] => AnimalType.Ferret
      case t if  t =:= typeOf[Animal.GuineaPig] => AnimalType.GuineaPig
      case t if  t =:= typeOf[Animal.Hamster] => AnimalType.Hamster
      case t if  t =:= typeOf[Animal.Hedgehog] => AnimalType.Hedgehog
      case t if  t =:= typeOf[Animal.Mouse] => AnimalType.Mouse
      case t if  t =:= typeOf[Animal.Pig] => AnimalType.Pig
      case t if  t =:= typeOf[Animal.Rabbit] => AnimalType.Rabbit
      case t if  t =:= typeOf[Animal.Rat] => AnimalType.Rat
      case t if  t =:= typeOf[Animal.Snake] => AnimalType.Snake
      case _ => throw new EnumerationNotFoundException("The provided Animal value was not found")
    }
  }
  
}