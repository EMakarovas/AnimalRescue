package com.emakarovas.animalrescue.persistence.dao

import com.emakarovas.animalrescue.model.PostingAnimalModel
import scala.concurrent.Future

trait PostingAnimalDAO extends AbstractModelDAO[PostingAnimalModel] {
  def findByPostingId(postingId: String): Future[List[PostingAnimalModel]]
}