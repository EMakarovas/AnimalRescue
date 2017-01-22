package com.emakarovas.animalrescue.model

import java.util.Date

case class PostingModel
  (override val id: String,
   date: Date,
   text: String)
   extends AbstractModel(id) {
  
}