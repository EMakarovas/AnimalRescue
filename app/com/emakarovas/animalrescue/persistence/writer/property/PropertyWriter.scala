package com.emakarovas.animalrescue.persistence.writer.property

import com.emakarovas.animalrescue.model.AbstractModel
import com.emakarovas.animalrescue.model.AbstractPersistableEntity

import reactivemongo.bson.BSONValue
import reactivemongo.bson.BSONWriter

/**
 * Responsible for writing the properties of a given type, T, to BSON
 */
trait PropertyWriter[T <: AbstractModel with AbstractPersistableEntity] extends BSONWriter[Any, BSONValue]