package com.innov.wakasinglebase.data.source


import com.innov.wakasinglebase.data.model.TemplateModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by innov Victor on 4/3/2023.
 */
object TemplateDataSource {

    private suspend fun getImageFromDb():List<TemplateModel>{
        return emptyList()
    }
 

    fun fetchTemplates(): Flow<List<TemplateModel>> = flow {
      val templates=  getImageFromDb()
        emit(templates.shuffled())
    }
}