package com.innov.wakasinglebase.screens.camera.publication

sealed class UploadDataEvent {
    object EventUploadData : UploadDataEvent()


}

data class ViewState(
    val responseUpload: String? = null,


)