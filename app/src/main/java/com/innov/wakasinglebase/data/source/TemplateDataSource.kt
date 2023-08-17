package com.innov.wakasinglebase.data.source

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.innov.wakasinglebase.data.model.TemplateModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

/**
 * Created by innov Victor on 4/3/2023.
 */
object TemplateDataSource {

    private suspend fun getImageFromDb():List<TemplateModel>{
        val db=FirebaseFirestore.getInstance();
       val snap= db.collection("gifts").get().await()
       return snap.documents.map {
            TemplateModel(
                id = it.id,
                name = it.data?.get("title").toString(),
                hint = it.data?.get("cost").toString(),
                mediaUrl = it.data?.get("image").toString()
            )
        }
    }
 

    fun fetchTemplates(): Flow<List<TemplateModel>> = flow {
      val templates=  getImageFromDb()
        emit(templates.shuffled())
    }
}