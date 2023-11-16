package com.innov.wakasinglebase.screens.market


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.innov.wakasinglebase.core.extension.LargeSpace
import com.innov.wakasinglebase.core.extension.Space
import com.innov.wakasinglebase.ui.theme.PrimaryColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketScreen(
    navController: NavController,
    viewModel: MarketViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.onTriggerEvent(MarketMediaEvent.EventFetchTemplate)
    }


    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Events") }, actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Outlined.Refresh, contentDescription = "")
                }
            })
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
                 horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                if (viewState?.isLoading == true) {
                    LinearProgressIndicator(
                        trackColor = PrimaryColor,
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                16.dp.Space()
            }


            if (viewState?.tickets != null) {
                viewState?.tickets?.let {tickets->
                    items(tickets) { ticket ->

                        Text("Miracle")

                    }
                }

                item {
                    80.dp.Space()
                }
            }
        }
        LargeSpace()

    }

}





