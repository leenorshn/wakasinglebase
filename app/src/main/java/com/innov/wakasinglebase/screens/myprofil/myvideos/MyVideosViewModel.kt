package com.innov.wakasinglebase.screens.myprofil.myvideos

import androidx.lifecycle.viewModelScope
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.core.base.BaseViewModel
import com.innov.wakasinglebase.domain.repository.VideoRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MyVideosViewModel @Inject constructor(
    private val repository: VideoRepositoryImpl
):BaseViewModel<ViewState,MyVideoEvent>() {
    override fun onTriggerEvent(event: MyVideoEvent) {
        when(event){
            is MyVideoEvent.OnVideoDeletedEvent -> {
                deleteMyVideo(event.id)
            }
            is MyVideoEvent.OnVideosLoadedEvent -> {
                loadMyVideos(event.uid)
            }
        }
    }

    private fun deleteMyVideo(vid:String){
        //delete video
    }

    private fun loadMyVideos(id:String){
        viewModelScope.launch {
            repository.getAuthorVideos(id).collect{
                when(it){
                    is BaseResponse.Error -> {
                        updateState(
                            ViewState(
                                isLoading = false,
                                error = it.error,
                                videos = emptyList()
                            )
                        )
                    }
                    BaseResponse.Loading -> {
                        updateState(
                            ViewState(
                                isLoading = true,
                                error = null,
                                videos = emptyList()
                            )
                        )
                    }
                    is BaseResponse.Success -> {
                        updateState(
                            ViewState(
                                isLoading = false,
                                error = null,
                                videos = it.data
                            )
                        )
                    }
                }
            }
        }
    }

}