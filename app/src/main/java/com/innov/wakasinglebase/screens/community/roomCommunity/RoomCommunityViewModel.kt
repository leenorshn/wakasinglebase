package com.innov.wakasinglebase.screens.community.roomCommunity

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.core.base.BaseViewModel
import com.innov.wakasinglebase.domain.CommunityRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RoomCommunityViewModel @Inject constructor(
    private val repository: CommunityRepositoryImpl,

    ) : BaseViewModel<ViewState, RoomCommunityEvent>() {

    //val followState = MutableStateFlow(FollowState())


    override fun onTriggerEvent(event: RoomCommunityEvent) {
        when (event) {

            is RoomCommunityEvent.OnLoadCommunity -> {
                loadCommunity(event.id)
            }
        }
    }


    private fun loadCommunity(id: String) {
        viewModelScope.launch {
            repository.getCommunity(id).collect {
                Log.d("COMMUNITY",id)
                when (it) {
                    is BaseResponse.Error -> {
                        updateState(
                            (viewState.value ?: ViewState()).copy(
                                error = it.error,
                                isLoading = false,
                                community = null,
                            )
                        )
                    }

                    BaseResponse.Loading -> {
                        updateState(
                            (viewState.value ?: ViewState()).copy(
                                error = null,
                                isLoading = true,
                                community = null,
                            )
                        )
                    }

                    is BaseResponse.Success -> {
                        updateState(
                            (viewState.value ?: ViewState()).copy(
                                error = null,
                                isLoading = false,
                                community = it.data
                            )
                        )
                        Log.d("COMMUNITY","${it.data?.name}")
                    }
                }
            }
        }
    }
}