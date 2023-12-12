package com.innov.wakasinglebase.data.model

import com.innov.wakasinglebase.core.utils.FileUtils
import com.wakabase.FindNotificationsQuery

data class NotificationModel(
    val id:String,
    val title:String,
    val message:String,
    val status:String,
    val file:String?,
    val createdAt:String?

)

fun FindNotificationsQuery.Nofication.toNotificationModel():NotificationModel{
    return NotificationModel(
        id=id,
        title=title,
        status = status,
        message = message,
        file = file,
        createdAt = FileUtils.convertUnixTimestampToReadableDate(createdAt.toLong()),
    )
}
