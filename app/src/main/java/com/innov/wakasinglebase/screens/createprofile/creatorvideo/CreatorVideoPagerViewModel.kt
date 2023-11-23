package com.innov.wakasinglebase.screens.createprofile.creatorvideo

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.innov.wakasinglebase.core.DestinationRoute.PassedKey.USER_ID
import com.innov.wakasinglebase.core.DestinationRoute.PassedKey.VIDEO_INDEX
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.core.base.BaseViewModel
import com.innov.wakasinglebase.data.model.VideoModel
import com.innov.wakasinglebase.domain.creatorprofile.GetCreatorProfileUseCase
import com.innov.wakasinglebase.domain.creatorprofile.GetCreatorPublicVideoUseCase
import com.innov.wakasinglebase.domain.creatorprofile.GetLikeVideoUseCase
import com.innov.wakasinglebase.screens.home.foryou.LikeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by innov Victor on 3/22/2023.
 */
@HiltViewModel
class CreatorVideoPagerViewModel
@Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getCreatorProfileUseCase: GetCreatorProfileUseCase,
    private val getCreatorPublicVideoUseCase: GetCreatorPublicVideoUseCase,
    private  val getLikeVideoUseCase: GetLikeVideoUseCase,
) : BaseViewModel<ViewState, CreatorVideoEvent>() {
    val userId: String? = savedStateHandle[USER_ID]
    val videoIndex: Int? = savedStateHandle[VIDEO_INDEX]

    private val _videosList = MutableStateFlow<List<VideoModel>>(ArrayList())
    val publicVideosList = _videosList.asStateFlow()

    override fun onTriggerEvent(event: CreatorVideoEvent) {
        when(event){
            is CreatorVideoEvent.OnLikeVideoEvent -> {
                likeVideo(event.videoId)
            }
        }
    }

    val likeState = mutableStateOf(LikeState())

    init {
        userId?.let {
            fetchCreatorVideo(it)
        }
    }


    private fun fetchCreatorVideo(id: String) {
        viewModelScope.launch {
            getCreatorPublicVideoUseCase(id).collect {
                when(it){
                    is BaseResponse.Error -> {
                        updateState(ViewState(error = it.error))
                    }
                    BaseResponse.Loading -> {
                        updateState(ViewState(isLoading = true))
                    }
                    is BaseResponse.Success -> {
                        updateState(ViewState(creatorVideosList = it.data))
                    }
                }

            }
        }
    }
    private  fun likeVideo(id:String){
        viewModelScope.launch {
            getLikeVideoUseCase.invoke(id).collect{
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

}

data class VideoState(
    val videos:List<VideoModel> = emptyList(),
    val error:String?=null,
    val isLoading:Boolean=false
)

data class LikeState(
    val isLiked:Boolean=false,
    val  error: String?=null,
    val isLoading:Boolean=false
)