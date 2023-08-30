package com.innov.wakasinglebase.screens.camera.publication

import android.content.Context
import android.net.Uri
import com.amplifyframework.core.Amplify
import com.innov.wakasinglebase.core.DestinationRoute
import com.innov.wakasinglebase.core.base.BaseViewModel
import com.innov.wakasinglebase.signin.utils.createVideo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PublicationViewModel @Inject constructor() : BaseViewModel<ViewState, UploadDataEvent>() {
    override fun onTriggerEvent(event: UploadDataEvent) {
        TODO("Not yet implemented")
//      when(event){
//          UploadDataEvent.EventUploadData->publishVideoData()
//          else ->{
//
//          }
//      }
    }

    private fun Context.publishVideoData(uri: Uri, fileName:String, category:String, title:String, description:String){
        val stream=  contentResolver.openInputStream(uri)

        Amplify.Storage.uploadInputStream(
            fileName,
            stream!!,
            {



            },

            {

            }
        )
    }
}