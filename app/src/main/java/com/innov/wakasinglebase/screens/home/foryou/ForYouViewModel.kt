package com.innov.wakasinglebase.screens.home.foryou

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.core.base.BaseViewModel
import com.innov.wakasinglebase.domain.repository.VideoRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by innov Victor on 3/15/2023.
 */
@HiltViewModel
class ForYouViewModel @Inject constructor(
    private val videoRepository: VideoRepositoryImpl,

) : BaseViewModel<ViewState, ForYouEvent>() {

    val likeState = mutableStateOf(LikeState())
    init {
        viewModelScope.launch {
            getForYouPageFeeds()
        }
    }

    override fun onTriggerEvent(event: ForYouEvent) {
        when(event){
            ForYouEvent.OnLoadVideo -> {
                viewModelScope.launch {
                    getForYouPageFeeds()
                }
            }

            is ForYouEvent.OnLikeVideo -> {
                likeVideo(event.videoId)
            }
        }
    }

    private  fun likeVideo(id:String){
        viewModelScope.launch {
            videoRepository.likeVideo(id).collect{
                when(it){
                    is BaseResponse.Error -> {
                        likeState.value=likeState.value.copy(
                            error=it.error,
                            isLiked = false,
                            isLoading = false,
                        )
                    }
                    BaseResponse.Loading -> {
                        likeState.value=likeState.value.copy(
                            error=null,
                            isLiked = false,
                            isLoading = true,
                        )
                    }
                    is BaseResponse.Success -> {
                        likeState.value=likeState.value.copy(
                            error=null,
                            isLiked = true,
                            isLoading = false,
                        )
                    }
                }
            }
        }
    }

    private suspend fun getForYouPageFeeds() = withContext(Dispatchers.IO){
        viewModelScope.launch {
            videoRepository.getAllVideos().collect{
                when(it){
                    is BaseResponse.Error -> {
                        updateState(ViewState(
                            error = "Loading error"
                        ))
                    }
                    BaseResponse.Loading -> {
                        updateState(ViewState(
                            isLoading = true
                        ))
                    }
                    is BaseResponse.Success -> {
                        updateState(ViewState(
                            forYouPageFeed = it.data,
                            isLoading = false
                        ))
                    }
                }
            }
        }
    }


}

data class LikeState(
    val isLiked:Boolean=false,
   val  error: String?=null,
    val isLoading:Boolean=false
)