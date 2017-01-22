package com.emakarovas.animalrescue.model

import java.util.Date

case class CommentModel
  (override val id: String,
   date: Date,
   text: String)
   extends AbstractModel(id) {
  
}