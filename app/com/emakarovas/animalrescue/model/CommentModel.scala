package com.emakarovas.animalrescue.model

import java.util.Date

case class CommentModel
  (override val id: String,
   date: Date,
   text: String,
   name: Option[String],
   userId: Option[String])
   extends AbstractModel(id)