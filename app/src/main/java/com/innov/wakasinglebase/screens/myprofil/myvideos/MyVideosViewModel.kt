package com.innov.wakasinglebase.screens.myprofil.myvideos

import androidx.lifecycle.viewModelScope
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.core.base.BaseViewModel
import com.innov.wakasinglebase.domain.repository.VideoRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MyVideosViewModel @Inject constructor(
    private val repository: VideoRepositoryImpl
):BaseViewModel<ViewState,MyVideoEvent>() {
    val deleteState= MutableStateFlow(DeleteState())
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
        viewModelScope.launch {
            repository.deleteVideo(vid).collect{
                when(it){
                    is BaseResponse.Error -> {
                        deleteState.value=deleteState.value.copy(
                            isLoading = false,
                            error = "Error of deleting my video",
                            success = false,
                        )
                    }
                    BaseResponse.Loading -> {
                        deleteState.value=deleteState.value.copy(
                            isLoading = true,
                            error = null,
                            success = false,
                        )
                    }
                    is BaseResponse.Success -> {
                        deleteState.value=deleteState.value.copy(
                            isLoading = false,
                            error = null,
                            success = true,
                        )
                    }
                }
            }
        }
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