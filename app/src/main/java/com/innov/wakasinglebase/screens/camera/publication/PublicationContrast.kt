package com.innov.wakasinglebase.screens.camera.publication

import android.net.Uri

sealed class UploadDataEvent {
    class EventUploadData(uri: Uri,fileName:String,title:String,description:String,typeVideo:String) : UploadDataEvent()
}

data class ViewState(
    val responseUpload: String? = null,
)

data class ThumbNailState(
    val loadThumbNail:Boolean=false
)

