package com.emakarovas.animalrescue.util.builder

import com.emakarovas.animalrescue.model.SearchModel
import com.google.inject.ImplementedBy
import com.emakarovas.animalrescue.util.builder.impl.DefaultSearchURLBuilder

@ImplementedBy(classOf[DefaultSearchURLBuilder])
trait SearchURLBuilder extends AbstractURLBuilder[SearchModel] {
  def build(search: SearchModel): String
}