package com.innov.wakasinglebase.screens.notification

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.innov.wakasinglebase.R
import com.innov.wakasinglebase.core.extension.Space
import com.innov.wakasinglebase.ui.theme.PrimaryColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    navController: NavController,
    viewModel: NotificationViewModel = hiltViewModel()
) {

    val viewState by viewModel.viewState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Notifications") },
                actions = {
                    IconButton(onClick = {
                        viewModel.onTriggerEvent(NotificationEvent.LoadNotifications)
                    }) {
                        Icon(Icons.Outlined.Refresh, null)
                    }
                })
        }
    ) { p ->
        if (viewState?.isLoading == true) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(p),
                color = PrimaryColor,
            )
        }
        if (viewState?.notifications?.isNotEmpty() == false) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(p), contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(painter = painterResource(id = R.drawable.empty), contentDescription = "")
                    10.dp.Space()
                    Text(text = "notification list is Empty")
                    10.dp.Space()
                    OutlinedButton(onClick = {
                        viewModel.onTriggerEvent(NotificationEvent.LoadNotifications)
                    }) {
                        Text(text = "Refresh")
                    }
                }
            }
        } else {
            viewState?.notifications?.let {
                LazyColumn(
                    modifier = Modifier
                        .padding(p)
                        .fillMaxSize()
                ) {
                    items(it) { notif ->
                        Card {
                            val color = if (notif.status == "CRITIQUE") Color.Red else Color.Blue
                            ListItem(
                                leadingContent = {
                                    OutlinedIconButton(onClick = { /*TODO*/ }) {
                                        Icon(Icons.Outlined.Notifications, null)
                                    }
                                },
                                overlineContent = {
                                    Text(text = notif.status, fontSize = 12.sp, color = color)
                                },
                                headlineContent = {
                                    Text(
                                        text = notif.title,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                },
                                supportingContent = {
                                    Text(
                                        text = notif.message + "\n" + notif.createdAt,
                                        textAlign = TextAlign.Start,
                                        color = Color.Gray,
                                        fontSize = 14.sp
                                    )
                                },


                                )
                            Divider()
                        }
                    }
                }
            }
        }

    }
}