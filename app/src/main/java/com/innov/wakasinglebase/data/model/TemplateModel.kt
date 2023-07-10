package com.innov.wakasinglebase.data.model

import android.net.Uri

/**
 * Created by innov  on 4/2/2023.
 */
data class TemplateModel(
    val id: Long,
    val name: String,
    val hint: String,
    val mediaUrl: String
){
    fun parseMediaLink(): Uri = Uri.parse("file:///android_asset/templateimages/${mediaUrl}")
}