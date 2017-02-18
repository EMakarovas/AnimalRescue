package com.emakarovas.animalrescue.persistence.writer

import com.emakarovas.animalrescue.model.PostingModel

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class PostingWriter @Inject() (
    implicit val costWriter: CostWriter,
    implicit val geolocationWriter: GeolocationWriter) extends AbstractModelWriter[PostingModel] {
  override def write(posting: PostingModel): BSONDocument = {
    BSONDocument(
        "_id" -> posting.id,
        "startDate" -> posting.startDate,
        "endDate" -> posting.endDate,
        "text" -> posting.text,
        "costList" -> posting.costList,
        "geolocation" -> posting.geolocation,
        "userId" -> posting.userId,
        "available" -> posting.available)
  }
}