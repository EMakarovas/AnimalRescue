package com.emakarovas.animalrescue.model

case class VideoModel
  (override val id: String,
   url: String)
   extends AbstractModel(id)