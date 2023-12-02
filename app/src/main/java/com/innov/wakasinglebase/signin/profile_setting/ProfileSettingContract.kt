package com.innov.wakasinglebase.signin.profile_setting

import android.net.Uri
import com.innov.wakasinglebase.data.model.UserModel


data class ViewState(
    val isLoading:Boolean=false,
    val userModel: UserModel?=null,
    val error:String?=null
)

data class UpdateUserState(
    val isLoading:Boolean=false,
    val success: Boolean=false,
    val error:String?=null
)

data class ProfileSettingState(
    var name:String?=null,
    val avatar:String?=null,
    val bio:String?=null,
)

data class UploadImageState2(
    val success: Boolean=false,
    val isLoading: Boolean=false,
    val error: String?=null,
)

sealed class ProfileSettingEvent{
    data class OnNameEntered(val name:String): ProfileSettingEvent()



    data class OnAvatarEntered(val avatar: String):ProfileSettingEvent()

    data class OnUploadImageOns3(val uri: Uri, val fileName:String):ProfileSettingEvent()

    object ReloadUser:ProfileSettingEvent()

    object OnSubmit: ProfileSettingEvent()

}