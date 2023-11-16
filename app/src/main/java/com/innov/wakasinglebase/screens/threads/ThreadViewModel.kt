package com.innov.wakasinglebase.screens.threads

import androidx.lifecycle.viewModelScope
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.core.base.BaseViewModel
import com.innov.wakasinglebase.domain.threads.ThreadsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ThreadViewModel @Inject constructor(
    private val repository: ThreadsRepositoryImpl
) : BaseViewModel<ViewState, ThreadsEvent>() {

    init {
        getFriends()
    }
    override fun onTriggerEvent(event: ThreadsEvent) {
        when(event){
            ThreadsEvent.OnThreadsLoadEvent -> getFriends()
        }
    }

    private fun getFriends() {
        viewModelScope.launch {
            repository.getAllThreads().collect {
                when (it) {
                    is BaseResponse.Error -> {
                        updateState(
                            (viewState.value ?: ViewState()).copy(
                                error = it.error,
                                isLoading = false,
                                threads = null
                            )
                        )
                    }

                    BaseResponse.Loading -> {
                        updateState(
                            (viewState.value ?: ViewState()).copy(
                                error = null,
                                isLoading = true,
                                threads = null
                            )
                        )
                    }

                    is BaseResponse.Success -> {
                        updateState(
                            (viewState.value ?: ViewState()).copy(
                                error = null,
                                isLoading = false,
                                threads = it.data
                            )
                        )
                    }
                }
            }
        }
    }

}