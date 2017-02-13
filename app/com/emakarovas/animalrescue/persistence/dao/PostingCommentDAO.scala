package com.emakarovas.animalrescue.persistence.dao

import com.emakarovas.animalrescue.model.PostingCommentModel
import scala.concurrent.Future

trait PostingCommentDAO extends AbstractModelDAO[PostingCommentModel] {
  def findByPostingId(postingId: String): Future[List[PostingCommentModel]]
}