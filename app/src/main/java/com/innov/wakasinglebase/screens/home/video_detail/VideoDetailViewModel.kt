package com.innov.wakasinglebase.screens.home.video_detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.data.model.VideoModel
import com.innov.wakasinglebase.domain.repository.VideoRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class VideoDetailViewModel @Inject constructor(
    private val repository: VideoRepositoryImpl
) : ViewModel() {

    val state = mutableStateOf(VideoDetailState())

    fun onEvent(event: VideoDetailEvent) {
        when (event) {
            is VideoDetailEvent.OnFetchVideo -> {
                fetchVideo(id = event.videoId)
            }
        }
    }


    private fun fetchVideo(id: String) {
        viewModelScope.launch {
repository.getVideo(videoId = id).collect{
    when(it){
        is BaseResponse.Error -> {
            state.value=state.value.copy(
                error = it.error,
                isLoading = false,
                video = null
            )
        }
        BaseResponse.Loading -> {
            state.value=state.value.copy(
                error = null,
                isLoading = true,
                video = null
            )
        }
        is BaseResponse.Success -> {
            state.value=state.value.copy(
                error = null,
                isLoading = false,
                video = it.data
            )
        }
    }
}
        }
    }
}



data class VideoDetailState(
    val error:String?=null,
    val video:VideoModel?=null,
    val isLoading:Boolean=false
)
sealed class VideoDetailEvent {
    data class OnFetchVideo(val videoId: String) : VideoDetailEvent()
}