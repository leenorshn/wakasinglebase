package com.innov.wakasinglebase.signin.follower

import androidx.lifecycle.viewModelScope
import aws.smithy.kotlin.runtime.util.length
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.core.base.BaseViewModel
import com.innov.wakasinglebase.data.model.UserModel
import com.innov.wakasinglebase.domain.auth.AuthRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FollowerViewModel @Inject constructor(
    private val repository: AuthRepositoryImpl
):BaseViewModel<ViewState,FollowerEvent>() {
    init {
        loadUsers()
    }

    override fun onTriggerEvent(event: FollowerEvent) {
        when(event){
            is FollowerEvent.OnFollowingUser -> {
                addInFollowerList(event.user)
            }
            FollowerEvent.OnLoadUsers -> {
                loadUsers()
            }
        }
    }

    private fun addInFollowerList(user:UserModel){
        var list= mutableListOf<UserModel>()
        list.add(user)
        updateState((viewState.value ?: ViewState()).copy(followerList = list))
    }

    fun isValidFollowerList()= viewState.value?.followerList?.toList()?.length!! > 3


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