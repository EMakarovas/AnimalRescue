package com.emakarovas.animalrescue.persistence.writer

import com.emakarovas.animalrescue.model.VideoModel
import com.emakarovas.animalrescue.model.constants.VideoConstants
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants

import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class VideoWriter extends AbstractEntityWriter[VideoModel] {
  override def write(video: VideoModel): BSONDocument = {
    BSONDocument(
        MongoConstants.MongoId -> video.id,
        VideoConstants.Url -> video.url)
  }
}