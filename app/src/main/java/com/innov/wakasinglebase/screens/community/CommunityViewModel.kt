package com.innov.wakasinglebase.screens.community

import androidx.lifecycle.viewModelScope
import com.innov.wakasinglebase.core.base.BaseResponse
import com.innov.wakasinglebase.core.base.BaseViewModel
import com.innov.wakasinglebase.domain.CommunityRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val repository: CommunityRepositoryImpl
) : BaseViewModel<ViewState, CommunityEvent>() {

    init {
        getCommunities()
    }

    override fun onTriggerEvent(event: CommunityEvent) {
        when (event) {
            CommunityEvent.OnCommunityLoadEvent -> getCommunities()
        }
    }

    private fun getCommunities() {
        viewModelScope.launch {
            repository.getCommunities().collect {
                when (it) {
                    is BaseResponse.Error -> {
                        updateState(
                            ViewState(
                                isLoading = false,
                                error = it.error,
                                communities = null,
                            )
                        )
                    }

                    BaseResponse.Loading -> {
                        updateState(
                            ViewState(
                                isLoading = true,
                                error = null,
                                communities = null,
                            )
                        )
                    }

                    is BaseResponse.Success -> {
                        updateState(
                            ViewState(
                                isLoading = false,
                                error = null,
                                communities = it.data,
                            )
                        )
                    }
                }
            }
        }
    }

}