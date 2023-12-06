package com.innov.wakasinglebase.screens.myprofil.monetisation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.innov.wakasinglebase.R
import com.innov.wakasinglebase.common.CustomButton
import com.innov.wakasinglebase.core.extension.Space
import kotlinx.coroutines.launch

@Composable
fun MonetisationScreen(
    navController: NavController,
    viewModel: MonetisationViewModel= hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()



    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(key1 = viewState){
        if (
            viewState?.success==true
        ){
            coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = "Your demand have been sent!",
                    actionLabel = "Dismiss",
                    duration = SnackbarDuration.Long
                )
                navController.navigateUp()
            }

        }
    }
    Scaffold(
        scaffoldState=scaffoldState
    ) {
        Column(modifier= Modifier
            .padding(it)
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = R.drawable.monetisation),
                contentDescription ="",
            contentScale= ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp))
            10.dp.Space()
            Text(text = "Monetize Your Tam-Tam Experience!\n",
                    fontSize = 26.sp, fontWeight = FontWeight.SemiBold,
                )
            Text(text = stringResource(R.string.join_monetization), fontSize = 20.sp, lineHeight = 1.2.em)
            56.dp.Space()
            CustomButton(
                modifier=Modifier.width(200.dp),
                buttonText = "Join our program", shape = RoundedCornerShape(14)) {
                viewModel.onTriggerEvent(MonetisationEvent.OnMonetisationSend)
            }
        }
    }
}