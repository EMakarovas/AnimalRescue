package com.emakarovas.animalrescue.model

/**
 * Used with models that cannot be used without the depending ones
 * E.g. upon registration, a UserModel cannot exist without PersonModel,
 * or upon posting a PostingModel, PostingAnimal needs to exist first
 * Initialize the isAvailable variable as false at the beginning, then,
 * after the related models are saved, change isAvailable to true
 */
trait AvailableModel[T <: AvailableModel[T]] {
  val available: Boolean
  def buildAvailable: T
}