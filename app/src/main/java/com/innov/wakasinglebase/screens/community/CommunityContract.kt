package com.innov.wakasinglebase.screens.community

import com.innov.wakasinglebase.data.model.TemplateModel

data class ViewState(
    val templates: List<TemplateModel>? = null
)

sealed class CommunityMediaEvent {
    object EventFetchTemplate : CommunityMediaEvent()

}