package com.emakarovas.animalrescue.persistence.dao

import com.emakarovas.animalrescue.model.PostingModel
import scala.concurrent.Future

trait PostingDAO extends AbstractModelDAO[PostingModel] {
  def findByUserId(userId: String): Future[List[PostingModel]]
}