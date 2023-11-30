package com.innov.wakasinglebase.domain

import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.data.model.NotificationModel
import com.innov.wakasinglebase.data.repository.NotificationRepository
import com.innov.wakasinglebase.data.source.NotificationDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val dataSource: NotificationDataSource
):NotificationRepository {
    override suspend fun loadNotifications(): Flow<BaseResponse<List<NotificationModel>>> {
        return dataSource.getNotifications()
    }
}