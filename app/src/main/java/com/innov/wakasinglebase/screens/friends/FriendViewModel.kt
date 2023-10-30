package com.innov.wakasinglebase.screens.friends

import androidx.lifecycle.viewModelScope
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.core.base.BaseViewModel
import com.innov.wakasinglebase.domain.auth.AuthRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class FriendViewModel @Inject constructor(
    private val repository: AuthRepositoryImpl
) : BaseViewModel<ViewState, FriendEvent>() {

    init {
        getFriends()
    }
    override fun onTriggerEvent(event: FriendEvent) {
        when(event){
            FriendEvent.OnFriendLoadEvent -> getFriends()
        }
    }

    private fun getFriends() {
        viewModelScope.launch {
            repository.friends().collect {
                when (it) {
                    is BaseResponse.Error -> {
                        updateState(
                            (viewState.value ?: ViewState()).copy(
                                error = it.error,
                                isLoading = false
                            )
                        )
                    }

                    BaseResponse.Loading -> {
                        updateState(
                            (viewState.value ?: ViewState()).copy(
                                error = null,
                                isLoading = true
                            )
                        )
                    }

                    is BaseResponse.Success -> {
                        updateState(
                            (viewState.value ?: ViewState()).copy(
                                error = null,
                                isLoading = false,
                                friends = it.data
                            )
                        )
                    }
                }
            }
        }
    }

}