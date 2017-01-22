package com.emakarovas.animalrescue.model.relationship

import com.emakarovas.animalrescue.model.PostingModel
import com.emakarovas.animalrescue.model.CommentModel

case class Posting2CommentRelationship
  (override val id: String,
   override val sourceId: String,
   override val targetId: String) extends AbstractRelationship[PostingModel, CommentModel](id, sourceId, targetId) {
  
}