package com.emakarovas.animalrescue.model

import java.util.Date

abstract class AbstractCommentModel
  (override val id: String,
   date: Date,
   text: String)
   extends AbstractModel(id)