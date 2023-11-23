package com.innov.wakasinglebase.screens.community.newCommunity

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.core.base.BaseViewModel
import com.innov.wakasinglebase.domain.CommunityRepositoryImpl
import com.innov.wakasinglebase.domain.auth.AuthRepositoryImpl
import com.wakabase.type.NewCommunity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewCommunityViewModel @Inject constructor(
    private val userRepository: AuthRepositoryImpl,
    private val repository: CommunityRepositoryImpl
):BaseViewModel<ViewState,NewCommunityEvent>() {

    var uiState by mutableStateOf(NewCommunityState())

    var name by mutableStateOf("")
    var philosophy by mutableStateOf("")
    var members  = mutableListOf<String>()
    var subscription by mutableDoubleStateOf(0.0)
    override fun onTriggerEvent(event: NewCommunityEvent) {
        when(event){
            is NewCommunityEvent.OnMembersEnteredEvent -> {
                members.addAll(event.members)
                Log.d("COMMUNITY","${members.size} members")
            }
            is NewCommunityEvent.OnNameEnteredEvent -> {
                name=event.name
            }
            is NewCommunityEvent.OnPhilosophyEnteredEvent -> {
                philosophy=event.philosophy
            }
            NewCommunityEvent.OnSubmitEvent -> {
                submit()
            }
            is NewCommunityEvent.OnSubscriptionSelectedEvent -> {
                subscription=event.subscription
            }
            NewCommunityEvent.OnLoadFriendsEvent -> {
                loadFriends()
            }
        }
    }

    fun isValid():Boolean{
        return name.isNotEmpty() && philosophy.isNotEmpty() && subscription>0 && members.isNotEmpty()
    }

    private fun submit(){
        viewModelScope.launch {
           repository.createCommunity(data= NewCommunity(
               name=name,
               subscription=subscription,
               members = members!!,
               philosophy = philosophy,
           )).collect{
               when(it){
                   is BaseResponse.Error -> {
                       uiState=uiState.copy(
                           success = false,
                           error = it.error,
                           isLoading = false
                       )
                   }
                   BaseResponse.Loading -> {
                       uiState=uiState.copy(
                           success = false,
                           error = null,
                           isLoading = true
                       )
                   }
                   is BaseResponse.Success -> {
                       uiState=uiState.copy(
                           success = true,
                           error = null,
                           isLoading = false
                       )
                   }
               }
           }
        }
    }

    private fun loadFriends(){
        viewModelScope.launch {
            userRepository.myFriends().collect{
                when(it){
                    is BaseResponse.Error -> {
                        updateState(viewState=ViewState(
                            error = it.error,
                            isLoading = false,
                            friends = null,
                            success = false
                        ))
                    }
                    BaseResponse.Loading -> {
                        updateState(viewState=ViewState(
                            error = null,
                            isLoading = true,
                            friends = null,
                            success = false
                        ))
                    }
                    is BaseResponse.Success -> {
                        updateState(viewState=ViewState(
                            error = null,
                            isLoading = false,
                            friends = it.data,
                            success = true
                        ))
                    }
                }
            }
        }
    }
}