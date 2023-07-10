package com.innov.wakasinglebase.screens.explore

import androidx.lifecycle.viewModelScope

import com.innov.wakasinglebase.domain.following.GetContentCreatorsUseCase
//import com.innov.explore.FollowingEvent
import com.innov.wakasinglebase.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by innov Victor on 3/15/2023.
 */
@HiltViewModel
class FollowingViewModel @Inject constructor(
    private val getContentCreatorsUseCase: GetContentCreatorsUseCase
) : BaseViewModel<ViewState, FollowingEvent>() {
    override fun onTriggerEvent(event: FollowingEvent) {
    }

    init {
        getContentCreator()
    }

    private fun getContentCreator() {
        viewModelScope.launch {
            getContentCreatorsUseCase().collect {
                updateState(ViewState(contentCreators = it))
            }
        }
    }
}