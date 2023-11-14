package com.innov.wakasinglebase.screens.myprofil

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.innov.wakasinglebase.R
import com.innov.wakasinglebase.signin.AuthData

/**
 * Created by innov Victor on 4/1/2023.
 */

data class ViewState(
    val settingUiData: Map<String, List<RowItem>>? = null
)

sealed class SettingEvent {
    object OnLogout:SettingEvent()
}

data class UiState(
    val isSignInSuccessfull : Boolean = false,
    val signinError : String? = null,
    val currentUser: AuthData? = null,
    val isLoading : Boolean = false,

    )


data class RowItem(
    @DrawableRes val icon: Int,
    @StringRes val title: Int,
    val rightSideClickItem: @Composable (() -> Unit)? = null,
)

val settingUiModel: Map<String, List<RowItem>> = mapOf(
    "Account" to listOf(
        RowItem(icon = R.drawable.ic_dollar, title = R.string.balance),
        RowItem(icon = R.drawable.ic_graph, title = R.string.myvideo),
        RowItem(icon = R.drawable.ic_cle, title = R.string.mybusiness),
    ),
    "Support & About" to listOf(
        RowItem(icon = R.drawable.ic_support, title = R.string.about),
        RowItem(icon = R.drawable.ic_info, title = R.string.terms_and_policies)
    )
)