package com.innov.wakasinglebase.screens.createprofile.creatorprofile

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.innov.wakasinglebase.core.DestinationRoute.PassedKey.USER_ID
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.core.base.BaseViewModel
import com.innov.wakasinglebase.data.model.VideoModel
import com.innov.wakasinglebase.domain.creatorprofile.GetCreatorProfileFollowUseCase
import com.innov.wakasinglebase.domain.creatorprofile.GetCreatorProfileUseCase
import com.innov.wakasinglebase.domain.creatorprofile.GetCreatorPublicVideoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by innov Victor on 3/22/2023.
 */
@HiltViewModel
class CreatorProfileViewModel
@Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getCreatorProfileUseCase: GetCreatorProfileUseCase,
    private val getCreatorPublicVideoUseCase: GetCreatorPublicVideoUseCase,
    private val getCreatorProfileFollowUseCase: GetCreatorProfileFollowUseCase
) : BaseViewModel<ViewState, CreatorProfileEvent>() {
    val userId: String? = savedStateHandle[USER_ID]

    private val _publicVideosList = MutableStateFlow(PublicVideoState())
    val publicVideosList = _publicVideosList.asStateFlow()

    private val _likedVideosList = MutableStateFlow(LikedVideoState())
    val likedVideosList = _likedVideosList.asStateFlow()

    val followState= mutableStateOf(FollowState())

    override fun onTriggerEvent(event: CreatorProfileEvent) {
        when(event){
            is CreatorProfileEvent.OnFollowUser -> {
                followUser(event.id)
            }
        }
    }

    init {
        Log.e("USER_ID", userId ?: "$userId")
        userId?.let {
            fetchUser(it)
            fetchCreatorPublicVideo(it)
        }
    }

    private fun fetchUser(id: String) {
        viewModelScope.launch {
            getCreatorProfileUseCase(id).collect {
                when (it) {
                    is BaseResponse.Error -> {

                        updateState(
                            ViewState(
                                isLoading = false,
                                error = it.error,
                                creatorProfile = null
                            )
                        )

                    }

                    BaseResponse.Loading -> {
                        updateState(
                            ViewState(
                                isLoading = true,
                                error = null,
                                creatorProfile = null
                            )
                        )
                    }

                    is BaseResponse.Success -> {
                        updateState(ViewState(creatorProfile = it.data))
                    }
                }
            }
        }
    }

    private fun followUser(id: String) {
        viewModelScope.launch {
            getCreatorProfileFollowUseCase(id).collect {
                when (it) {
                    is BaseResponse.Error -> {

                       followState.value=followState.value.copy(
                           isLoading = false,
                           error = "Error of updating user",
                           success = false
                       )

                    }

                    BaseResponse.Loading -> {
                        followState.value=followState.value.copy(
                            isLoading = true,
                            error = null,
                            success = false,
                        )
                    }

                    is BaseResponse.Success -> {
                        followState.value=followState.value.copy(
                            isLoading = false,
                            error = null,
                            success = true,
                        )
                    }
                }
            }
        }
    }

    private fun fetchCreatorPublicVideo(id: String) {
        viewModelScope.launch {
            getCreatorPublicVideoUseCase(id).collect {
                //Log.d("DEBUG", "my video si ${it}")
                when (it) {
                    is BaseResponse.Error -> {
                        _publicVideosList.value = _publicVideosList.value.copy(
                            error = "Loading error"
                        )
                    }

                    BaseResponse.Loading -> {
                        _publicVideosList.value = _publicVideosList.value.copy(
                            isLoading = true,
                            error = null
                        )
                    }

                    is BaseResponse.Success -> {
                        _publicVideosList.value = _publicVideosList.value.copy(
                            isLoading = false,
                            error = null,
                            videos = it.data
                        )
                    }
                }
            }
        }
    }

}

data class PublicVideoState(
    val videos: List<VideoModel> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false
)

data class LikedVideoState(
    val videos: List<VideoModel> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false
)