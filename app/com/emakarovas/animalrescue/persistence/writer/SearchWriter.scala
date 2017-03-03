package com.emakarovas.animalrescue.persistence.writer

import com.emakarovas.animalrescue.model.SearchAnimalModel
import com.emakarovas.animalrescue.model.SearchModel
import com.emakarovas.animalrescue.model.SearchTerminationReasonModel
import com.emakarovas.animalrescue.model.constants.SearchAnimalConstants
import com.emakarovas.animalrescue.model.constants.SearchConstants
import com.emakarovas.animalrescue.model.constants.SearchTerminationReasonConstants
import com.emakarovas.animalrescue.persistence.dao.constants.MongoConstants
import com.emakarovas.animalrescue.persistence.writer.enumeration.AnimalTypeWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.GenderWriter
import com.emakarovas.animalrescue.persistence.writer.enumeration.SearchTerminationReasonWriter

import javax.inject.Inject
import javax.inject.Singleton
import reactivemongo.bson.BSONDocument

@Singleton
class SearchWriter @Inject() (
    implicit val locationWriter: LocationWriter,
    implicit val commentWriter: CommentWriter,
    implicit val animalTypeWriter: AnimalTypeWriter,
    implicit val genderWriter: GenderWriter,
    implicit val searchTerminationReasonEnumWriter: SearchTerminationReasonWriter) extends AbstractEntityWriter[SearchModel] {
  
  implicit object searchTerminationReasonWriter extends AbstractEntityWriter[SearchTerminationReasonModel] {
    override def write(searchTerminationReason: SearchTerminationReasonModel): BSONDocument = {
      BSONDocument(
          SearchTerminationReasonConstants.SearchTerminationReason -> searchTerminationReason.searchTerminationReason,
          SearchTerminationReasonConstants.Text -> searchTerminationReason.text)
    }
  }
  
  implicit object searchAnimalWriter extends AbstractEntityWriter[SearchAnimalModel] {
    override def write(searchAnimal: SearchAnimalModel): BSONDocument = {
      BSONDocument(
          MongoConstants.MongoId -> searchAnimal.id,
          SearchAnimalConstants.AnimalType -> searchAnimal.animalType,
          SearchAnimalConstants.SpecificType -> searchAnimal.specificType,
          SearchAnimalConstants.Gender -> searchAnimal.gender,
          SearchAnimalConstants.MinAge -> searchAnimal.minAge,
          SearchAnimalConstants.MaxAge -> searchAnimal.maxAge,
          SearchAnimalConstants.PotentialAnimalIdList -> searchAnimal.potentialAnimalIdList,
          SearchAnimalConstants.SearchTerminationReason -> searchAnimal.searchTerminationReason)
    }
  }

  def write(search: SearchModel): BSONDocument = {
    BSONDocument(
        MongoConstants.MongoId -> search.id,
        SearchConstants.Url -> search.url,
        SearchConstants.SearchAnimalList -> search.searchAnimalList,
        SearchConstants.Location -> search.location,
        SearchConstants.CommentList -> search.commentList,
        SearchConstants.StartDate -> search.startDate,
        SearchConstants.EndDate -> search.endDate,
        SearchConstants.IsPublic -> search.isPublic,
        SearchConstants.UserId -> search.userId)
  }
  
}