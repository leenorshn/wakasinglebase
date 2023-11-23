package com.innov.wakasinglebase.screens.conversation.newchat

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.core.base.BaseViewModel
import com.innov.wakasinglebase.domain.ChatRepositoryImpl
import com.innov.wakasinglebase.domain.auth.AuthRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewChatViewModel @Inject constructor(
   private val repository: AuthRepositoryImpl,
    private val chatRepository: ChatRepositoryImpl
):BaseViewModel<ViewState,NewChatEvent>() {
    val conversationState = mutableStateOf(ConversationState())
    init {
        getUsers()
    }
    override fun onTriggerEvent(event: NewChatEvent) {
        when(event){
            NewChatEvent.OnLoadUsersEvent -> {
                getUsers()
            }

            is NewChatEvent.OnStartedConversation -> {
                startConversation(event.userId)
            }
        }
    }

    private fun startConversation( id:String){
        viewModelScope.launch {
            chatRepository.createConversation(id).collect{
                when(it){
                    is BaseResponse.Error -> {
                        conversationState.value=conversationState.value.copy(
                            error=it.error,
                            isLoading = false,
                            success = false
                        )
                    }
                    BaseResponse.Loading -> {
                        conversationState.value=conversationState.value.copy(
                            error=null,
                            isLoading = true,
                            success = false
                        )
                    }
                    is BaseResponse.Success -> {
                        conversationState.value=conversationState.value.copy(
                            error=null,
                            isLoading = false,
                            success = true
                        )
                    }
                }
            }
        }
    }

    private  fun getUsers()  {
        viewModelScope.launch {
            repository.users().collect {
                when (it) {
                    is BaseResponse.Error -> {

                        updateState(
                            (viewState.value ?: ViewState()).copy(
                                error = it.error,
                                isLoading = false,
                                users = null
                            )
                        )
                    }

                    BaseResponse.Loading -> {
                        updateState(
                            (viewState.value ?: ViewState()).copy(
                                isLoading = true,
                                error = null,
                                users = null
                            )
                        )
                    }

                    is BaseResponse.Success -> {
                        updateState(
                            (viewState.value ?: ViewState()).copy(
                                users = it.data,
                                isLoading = false,
                                error = null
                            )
                        )
                    }
                }
            }
        }
    }
}