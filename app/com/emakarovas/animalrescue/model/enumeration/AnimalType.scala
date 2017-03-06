package com.emakarovas.animalrescue.model.enumeration

sealed trait AnimalType extends Enum[AnimalType]
object AnimalType extends Enum[AnimalType] {
  case object Dog extends AnimalType
  case object Cat extends AnimalType
  case object Rat extends AnimalType
  case object Pig extends AnimalType
  case object GuineaPig extends AnimalType
  case object Ferret extends AnimalType
  case object Rabbit extends AnimalType
  case object Mouse extends AnimalType
  case object Fish extends AnimalType
  case object Bird extends AnimalType
  case object Hedgehog extends AnimalType
  case object Hamster extends AnimalType
  case object Chinchilla extends AnimalType
  case object Snake extends AnimalType
}