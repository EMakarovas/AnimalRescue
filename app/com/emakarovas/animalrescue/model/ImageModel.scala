package com.emakarovas.animalrescue.model

case class ImageModel
  (override val id: String,
   url: String)
   extends AbstractModel(id)