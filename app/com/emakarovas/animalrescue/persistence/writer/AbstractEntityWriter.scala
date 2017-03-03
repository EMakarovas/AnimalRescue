package com.emakarovas.animalrescue.persistence.writer

import reactivemongo.bson.BSONDocumentWriter
import com.emakarovas.animalrescue.model.AbstractEntity

abstract class AbstractEntityWriter[T <: AbstractEntity] extends BSONDocumentWriter[T] {

}