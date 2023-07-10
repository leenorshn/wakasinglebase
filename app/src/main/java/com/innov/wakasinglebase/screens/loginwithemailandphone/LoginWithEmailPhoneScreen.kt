package com.innov.wakasinglebase.screens.loginwithemailandphone

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.innov.wakasinglebase.common.TopBar
import com.innov.wakasinglebase.R
import com.innov.wakasinglebase.screens.loginwithemailandphone.tabs.PhoneTabScreen

/**
 * Created by innov Victor on 3/27/2023.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginWithEmailPhoneScreen(
    //navController: NavController,
    viewModel: LoginWithEmailPhoneViewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        TopBar(
            title = stringResource(id = R.string.login_or_sign_up),
            actions = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_question_circle),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
        ) {
           // navController.navigateUp()
        }
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Waka-Waka",
            style = TextStyle(
                color=Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium
            )
            )
            LoginPager(viewModel)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun LoginPager(viewModel: LoginWithEmailPhoneViewModel) {
    val pagerState = rememberPagerState()

    LaunchedEffect(key1 = pagerState) {
        snapshotFlow { pagerState.settledPage }.collect {
            viewModel.onTriggerEvent(LoginEmailPhoneEvent.EventPageChange(it))
        }
    }

    PhoneTabScreen(viewModel)



}