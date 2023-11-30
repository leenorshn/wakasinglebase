package com.innov.wakasinglebase.screens.notification

import com.innov.wakasinglebase.data.model.NotificationModel


data class ViewState(
    val notifications:List<NotificationModel> = emptyList(),
    val isLoading:Boolean=false,
    val error:String?=null,
)

sealed class NotificationEvent{
    object LoadNotifications:NotificationEvent()
}