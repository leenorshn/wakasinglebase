package com.innov.wakasinglebase.data.source

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.data.model.NotificationModel
import com.innov.wakasinglebase.data.model.toNotificationModel
import com.wakabase.FindNotificationsQuery

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NotificationDataSource @Inject constructor(
    val apolloClient: ApolloClient
) {

    fun getNotifications():Flow<BaseResponse<List<NotificationModel>>>{
        return flow {
            emit(BaseResponse.Loading)
            val resp=apolloClient.query(FindNotificationsQuery()).fetchPolicy(FetchPolicy.NetworkFirst).execute()
            if (resp.hasErrors()){
                emit(BaseResponse.Error("Error of loading notifications"))
            }

            val data=resp.data?.nofications?.map {
                it.toNotificationModel()
            }?: emptyList()
            emit(BaseResponse.Success(data))
        }
    }
}