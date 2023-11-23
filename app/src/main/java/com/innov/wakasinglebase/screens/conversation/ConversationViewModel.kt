package com.innov.wakasinglebase.screens.conversation


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
class ConversationViewModel @Inject constructor(
    private val repository: AuthRepositoryImpl,
    private val chatRepository: ChatRepositoryImpl,
) : BaseViewModel<ViewState, ConversationEvent>() {

    val conversationState = mutableStateOf(ChatViewState())
    val uiState= mutableStateOf(ConversationState())

    init {

        getChats()
    }

    override fun onTriggerEvent(event: ConversationEvent) {
        when (event) {
            ConversationEvent.EventFetchUserEvent -> {
                getChats()

            }

        }
    }





    private  fun getChats()  {
        viewModelScope.launch {
            chatRepository.getConversations().collect {
                when (it) {
                    is BaseResponse.Error -> {

                        conversationState.value =conversationState.value.copy(
                            conversations = null,
                            isLoading = false,
                            error = it.error
                        )
                    }

                    BaseResponse.Loading -> {
                        conversationState.value =conversationState.value.copy(
                            conversations = null,
                            isLoading = true,
                            error = null
                        )
                    }

                    is BaseResponse.Success -> {
                       conversationState.value =conversationState.value.copy(
                                conversations = it.data,
                                isLoading = false,
                                error = null
                            )

                    }
                }
            }
        }
    }

}