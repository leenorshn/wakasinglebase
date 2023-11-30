package com.innov.wakasinglebase.data.repository

import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.data.model.NotificationModel
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {

    suspend fun loadNotifications():Flow<BaseResponse<List<NotificationModel>>>
}