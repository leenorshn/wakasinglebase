package com.innov.wakasinglebase.screens.myprofil.mybusiness

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.core.base.BaseViewModel
import com.innov.wakasinglebase.domain.threads.ThreadsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyBusinessViewModel @Inject constructor(
    private val repository: ThreadsRepositoryImpl
) :BaseViewModel<ViewState,MyBusinessEvent>() {

    init {
        getMyThreads()
    }
    override fun onTriggerEvent(event: MyBusinessEvent) {
        when(event){
            MyBusinessEvent.LoadThreadsEvent -> {
                getMyThreads()
            }
        }
    }

    private fun getMyThreads(){
        viewModelScope.launch {
            repository.getMyThreads().collect{
                when(it){
                    is BaseResponse.Error -> {
                        Log.e("WAKA",it.error)
                        updateState(
                           ViewState(
                                isLoading = false,
                                error = it.error,
                                myThreads = null
                            )
                        )
                    }
                    BaseResponse.Loading -> {
                        Log.e("WAKA","Loading ...")
                        updateState(
                            ViewState(
                                isLoading = true,
                                error = null,
                                myThreads = null
                            )
                        )
                    }
                    is BaseResponse.Success -> {
                        Log.e("WAKA","Success ")
                        updateState(
                            ViewState(
                                isLoading = false,
                                error = null,
                                myThreads = it.data
                            )
                        )
                    }
                }
            }
        }
    }
}