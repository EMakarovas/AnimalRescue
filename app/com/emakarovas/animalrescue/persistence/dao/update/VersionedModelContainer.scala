package com.emakarovas.animalrescue.persistence.dao.update

import com.emakarovas.animalrescue.model.AbstractPersistableEntity
import com.emakarovas.animalrescue.model.AbstractModel

case class VersionedModelContainer[T <: AbstractModel with AbstractPersistableEntity](
    model: T,
    version: Int)