package com.innov.wakasinglebase.screens.home.foryou

import androidx.lifecycle.viewModelScope
import com.innov.wakasinglebase.core.base.BaseViewModel
import com.innov.wakasinglebase.domain.foryou.GetForYouPageFeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by innov Victor on 3/15/2023.
 */
@HiltViewModel
class ForYouViewModel @Inject constructor(
    private val getForYouPageFeedUseCase: GetForYouPageFeedUseCase
) : BaseViewModel<ViewState, ForYouEvent>() {
    init {
        getForYouPageFeeds()
    }

    override fun onTriggerEvent(event: ForYouEvent) {
    }

    private fun getForYouPageFeeds() {
        viewModelScope.launch {
            getForYouPageFeedUseCase().collect {
                updateState(ViewState(forYouPageFeed = it))
            }
        }
    }


}