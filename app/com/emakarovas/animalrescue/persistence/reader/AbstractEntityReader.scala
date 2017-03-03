package com.emakarovas.animalrescue.persistence.reader

import reactivemongo.bson.BSONDocumentReader
import com.emakarovas.animalrescue.model.AbstractEntity

// Might be useful later on
abstract class AbstractEntityReader[T <: AbstractEntity] extends BSONDocumentReader[T] {

}