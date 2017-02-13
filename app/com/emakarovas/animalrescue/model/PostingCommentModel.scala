package com.emakarovas.animalrescue.model

import java.util.Date

case class PostingCommentModel
  (override val id: String,
   date: Date,
   text: String,
   postingId: String)
   extends AbstractCommentModel(id, date, text) {
  
}