package com.innov.wakasinglebase.screens.notification

import androidx.lifecycle.viewModelScope
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.core.base.BaseViewModel
import com.innov.wakasinglebase.domain.NotificationRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val repository: NotificationRepositoryImpl
):BaseViewModel<ViewState,NotificationEvent>() {

    init {
        load()
    }
    override fun onTriggerEvent(event: NotificationEvent) {
        when(event){
            NotificationEvent.LoadNotifications -> {
                load()
            }
        }
    }

    private fun load(){
        viewModelScope.launch {
            repository.loadNotifications().collect{
                when(it){
                    is BaseResponse.Error -> {
                        updateState(viewState= ViewState(
                            isLoading = false,
                            notifications = emptyList(),
                            error = it.error
                        )
                        )
                    }
                    BaseResponse.Loading -> {
                        updateState(viewState=ViewState(
                            isLoading = true,
                            notifications = emptyList(),
                            error = null
                        ))
                    }
                    is BaseResponse.Success -> {
                        updateState(viewState=ViewState(
                            isLoading = false,
                            notifications = it.data,
                            error = null
                        ))
                    }
                }
            }
        }
    }
}