package com.innov.wakasinglebase.screens.camera

import android.database.Cursor
import android.net.Uri
import android.os.Parcelable

import android.provider.OpenableColumns
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.innov.wakasinglebase.R
import com.innov.wakasinglebase.data.model.TemplateModel
import com.innov.wakasinglebase.signin.AuthData
import kotlinx.parcelize.Parcelize


/**
 * Created by innov Victor on 4/3/2023.
 */
data class ViewState(
    val templates: List<TemplateModel>? = null,
    val currentUser:AuthData?=null
)
@Parcelize
data class UploadData(
    val uri: Uri,
    val fileName:String
): Parcelable

sealed class CameraMediaEvent {
    object EventFetchTemplate : CameraMediaEvent()
    object EventFetchCurrentUser:CameraMediaEvent()
}

sealed class UploadEvent {
    object GetUri : UploadEvent()
    object GetFileName:UploadEvent()

}

enum class Tabs(@StringRes val rawValue: Int) {
    CAMERA(rawValue = R.string.camera),
//    STORY(rawValue = R.string.story),
//    TEMPLATES(rawValue = R.string.templates)
}

enum class PermissionType {
    CAMERA,
    MICROPHONE
}

enum class CameraController(
    @StringRes val title: Int,
    @DrawableRes val icon: Int
) {
    FLIP(title = R.string.flip, icon = R.drawable.ic_flip),
   // SPEED(title = R.string.speed, icon = R.drawable.ic_speed),
  //  BEAUTY(title = R.string.beauty, icon = R.drawable.ic_profile_fill),
//    FILTER(title = R.string.filters, icon = R.drawable.ic_filter),
//    MIRROR(title = R.string.mirror, icon = R.drawable.ic_mirror),
//    TIMER(title = R.string.timer, icon = R.drawable.ic_timer),
    FLASH(title = R.string.flash, icon = R.drawable.ic_flash),
}

enum class CameraCaptureOptions(val value: String) {
    SIXTY_SECOND("90s"),
    FIFTEEN_SECOND("60s"),
    TRENTE_SECOND("30s"),
    //PHOTO("Photo"),
    VIDEO("Video"),
    TEXT("Text"),
}





