package com.innov.wakasinglebase.screens.home.foryou

import com.innov.wakasinglebase.data.model.UserModel
import com.innov.wakasinglebase.data.model.VideoModel


/**
 * Created by innov Victor on 3/15/2023.
 */
data class ViewState(
    val forYouPageFeed: List<VideoModel>? = null,
    val isLoading: Boolean? = null,
    val error: String? = null,
)

sealed class ForYouEvent {
}

sealed class Result<T>(val data : T? = null, val e : Throwable? = null){
    object Loading : Result<Nothing>()
    class Success<T>(data: T?) : Result<T>(data)
    class Error<T>(e: Throwable) : Result<T>(e= e)
}

