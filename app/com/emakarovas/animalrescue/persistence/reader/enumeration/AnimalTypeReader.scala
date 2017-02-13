package com.emakarovas.animalrescue.persistence.reader.enumeration

import com.emakarovas.animalrescue.model.enumeration.AnimalType

import javax.inject.Singleton
import reactivemongo.bson.BSONReader
import reactivemongo.bson.BSONString
import reactivemongo.bson.BSONValue

@Singleton
class AnimalTypeReader extends EnumerationReader[AnimalType] {
  def read(bson: BSONValue): AnimalType =
    bson match {
      case BSONString("Bird") => AnimalType.Bird
      case BSONString("Cat") => AnimalType.Cat
      case BSONString("Chinchilla") => AnimalType.Chinchilla
      case BSONString("Dog") => AnimalType.Dog
      case BSONString("Ferret") => AnimalType.Ferret
      case BSONString("Fish") => AnimalType.Fish
      case BSONString("GuineaPig") => AnimalType.GuineaPig
      case BSONString("Hamster") => AnimalType.Hamster
      case BSONString("Hedgehog") => AnimalType.Hedgehog
      case BSONString("Mouse") => AnimalType.Mouse
      case BSONString("Pig") => AnimalType.Pig
      case BSONString("Rabbit") => AnimalType.Rabbit
      case BSONString("Rat") => AnimalType.Rat
      case BSONString("Snake") => AnimalType.Snake
      case _ => throw new EnumerationNotFoundException
    }
}