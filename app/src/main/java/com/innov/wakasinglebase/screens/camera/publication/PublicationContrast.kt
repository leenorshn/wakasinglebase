package com.innov.wakasinglebase.screens.camera.publication

import android.graphics.Bitmap
import android.net.Uri

sealed class PublicationEvent {
 data  class OnCreateVideoEvent(
       val fileName: String,
       val category: String,
       val title: String,
       val thumbnail: String,
       val description: String
    ) : PublicationEvent()
    data class OnVideoUpload(val uri: Uri,val fileName: String):PublicationEvent()
    data class OnThumbnailUpload(val bitmap: Bitmap?,val filename:String):PublicationEvent()
}

data class ViewState(
    val success:Boolean=false,
    val isLoading:Boolean=false,
    val error:String?=null,
)

data class UploadVideoState(
    val success: Boolean=false,
    val isLoading: Boolean=false,
    val error: String?=null,
)



data class ThumbNailState(
    val loadThumbNail: Boolean = false
)

