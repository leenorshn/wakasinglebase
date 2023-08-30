package com.innov.wakasinglebase.screens.camera.publication

import android.net.Uri
import com.innov.wakasinglebase.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private fun publishVideoData(uri: Uri, fileName:String, category:String, title:String, description:String){}
}