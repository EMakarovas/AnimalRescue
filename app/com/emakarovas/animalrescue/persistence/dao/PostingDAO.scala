package com.emakarovas.animalrescue.persistence.dao

import com.emakarovas.animalrescue.model.PostingModel
import scala.concurrent.Future
import com.google.inject.ImplementedBy
import com.emakarovas.animalrescue.persistence.dao.impl.DefaultPostingDAO

@ImplementedBy(classOf[DefaultPostingDAO])
trait PostingDAO extends AbstractModelDAO[PostingModel] with AbstractAvailableModelDAO[PostingModel] {
  def findByUserId(userId: String): Future[List[PostingModel]]
}