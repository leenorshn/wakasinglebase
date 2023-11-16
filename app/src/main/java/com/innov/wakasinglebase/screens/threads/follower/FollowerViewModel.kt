package com.innov.wakasinglebase.screens.threads.follower

import androidx.lifecycle.viewModelScope
import aws.smithy.kotlin.runtime.util.length
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.core.base.BaseViewModel
import com.innov.wakasinglebase.domain.auth.AuthRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FollowerViewModel @Inject constructor(
    private val repository: AuthRepositoryImpl
):BaseViewModel<ViewState, FollowerEvent>() {

    val followState = MutableStateFlow(FollowState())
    init {
        loadUsers()
    }

    override fun onTriggerEvent(event: FollowerEvent) {
        when(event){
            is FollowerEvent.OnFollowingUser -> {
                followerUser(event.uid)
                addInFollowerList(event.uid)

            }
            FollowerEvent.OnLoadUsers -> {
                loadUsers()
            }
        }
    }

    private fun followerUser(id:String){
        viewModelScope.launch {
            repository.follow(id).collect{
                when(it){
                    is BaseResponse.Error -> {
                        followState.value=followState.value.copy(
                            error=it.error,
                            done = false,
                            isLoading = false
                        )
                    }
                    BaseResponse.Loading -> {
                        followState.value=followState.value.copy(
                            error=null,
                            done = false,
                            isLoading = true
                        )
                    }
                    is BaseResponse.Success -> {
                        followState.value=followState.value.copy(
                            error=null,
                            done = true,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }



    private fun addInFollowerList(user:String){
        var list= mutableListOf<String>()
        list.add(user)
        updateState((viewState.value ?: ViewState()).copy(followerList = list))
    }

    fun isValidFollowerList()= viewState.value?.followerList?.toList()?.length!! >= 3


    private fun loadUsers(){
        viewModelScope.launch {
            repository.users().collect{
                when(it){
                    is BaseResponse.Error -> {
                        updateState((viewState.value ?: ViewState()).copy(error = it.error, isLoading = false))
                    }
                    BaseResponse.Loading -> {
                        updateState((viewState.value ?: ViewState()).copy(error = null, isLoading = true))
                    }
                    is BaseResponse.Success -> {
                        updateState((viewState.value ?: ViewState()).copy(error = null, isLoading = false, listUser = it.data))
                    }
                }
            }
        }
    }
}