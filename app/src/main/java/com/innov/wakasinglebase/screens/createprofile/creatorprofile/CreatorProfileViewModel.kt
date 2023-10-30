package com.innov.wakasinglebase.screens.createprofile.creatorprofile

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.innov.wakasinglebase.core.DestinationRoute.PassedKey.USER_ID
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.core.base.BaseViewModel
import com.innov.wakasinglebase.data.model.VideoModel
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
    private val getCreatorPublicVideoUseCase: GetCreatorPublicVideoUseCase
) : BaseViewModel<ViewState, CreatorProfileEvent>() {
    val userId: String? = savedStateHandle[USER_ID]

    private val _publicVideosList = MutableStateFlow(PublicVideoState())
    val publicVideosList = _publicVideosList.asStateFlow()

    private val _likedVideosList = MutableStateFlow(LikedVideoState())
    val likedVideosList = _likedVideosList.asStateFlow()

    override fun onTriggerEvent(event: CreatorProfileEvent) {
    }

    init {
        Log.e("USER_ID",userId?:"$userId")
        userId?.let {
            fetchUser(it)
            fetchCreatorPublicVideo(it)
        }
    }

    private fun fetchUser(id: String) {
        viewModelScope.launch {
            getCreatorProfileUseCase(id).collect {
                updateState(ViewState(creatorProfile = it))
            }
        }
    }

    private fun fetchCreatorPublicVideo(id: String) {
        viewModelScope.launch {
            getCreatorPublicVideoUseCase(id).collect {
                //Log.d("DEBUG", "my video si ${it}")
                when(it){
                    is BaseResponse.Error -> {
                        _publicVideosList.value = _publicVideosList.value.copy(
                            error="Loading error"
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
    val videos:List<VideoModel> = emptyList(),
    val error:String?=null,
    val isLoading:Boolean=false
)

data class LikedVideoState(
    val videos:List<VideoModel> = emptyList(),
    val error:String?=null,
    val isLoading:Boolean=false
)