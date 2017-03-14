package com.emakarovas.animalrescue.persistence.writer

import com.emakarovas.animalrescue.model.SearchModel
import com.emakarovas.animalrescue.model.constants.SearchConstants
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class SearchWriter @Inject() (
    implicit private val searchAnimalWriter: SearchAnimalWriter,
    implicit private val locationWriter: LocationWriter,
    implicit private val commentWriter: CommentWriter) extends AbstractEntityWriter[SearchModel] {

  def write(search: SearchModel): BSONDocument = {
    BSONDocument(
        MongoConstants.MongoId -> search.id,
        MongoConstants.Data -> BSONDocument(
            SearchConstants.Url -> search.url,
            SearchConstants.SearchAnimalList -> search.searchAnimalList,
            SearchConstants.Location -> search.location,
            SearchConstants.CommentList -> search.commentList,
            SearchConstants.StartDate -> search.startDate,
            SearchConstants.EndDate -> search.endDate,
            SearchConstants.IsPublic -> search.isPublic,
            SearchConstants.UserId -> search.userId))
  }
  
}