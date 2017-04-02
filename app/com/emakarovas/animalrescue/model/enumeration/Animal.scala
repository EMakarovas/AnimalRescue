package com.emakarovas.animalrescue.model.enumeration

/**
 * Used by {@link AnimalType} and {@link SpecificType}
 */
sealed trait Animal extends Enum[Animal]

object Animal extends Enum[Animal] {
  type Bird <: Animal
  type Cat <: Animal
  type Chinchilla <: Animal
  type Dog <: Animal
  type Ferret <: Animal
  type GuineaPig <: Animal
  type Hamster <: Animal
  type Hedgehog <: Animal
  type Mouse <: Animal
  type Pig <: Animal
  type Rabbit <: Animal
  type Rat <: Animal
  type Snake <: Animal  
}