package com.innov.wakasinglebase.screens.myprofil.mybusiness

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.innov.wakasinglebase.R
import com.innov.wakasinglebase.common.CustomButton
import com.innov.wakasinglebase.core.extension.Space
import com.innov.wakasinglebase.ui.theme.PrimaryColor
import com.innov.wakasinglebase.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBusinessScreen(
    navController: NavController,
    viewModel: MyBusinessViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("My threads")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO*/ },
                containerColor = PrimaryColor,
                contentColor = White,
            ) {
                Icon(imageVector = Icons.Outlined.AddCircle, contentDescription = "")
            }
        }
    ) {
        if (viewState?.isLoading == true) {
            LinearProgressIndicator(color = PrimaryColor, modifier = Modifier.fillMaxWidth())
        }

        if (viewState?.error != null) {
            Box(
                modifier = Modifier.fillMaxSize().padding(it),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text("Error of loading that")
                    CustomButton(
                        buttonText = "Reload",
                        shape = RoundedCornerShape(12)
                    ) {
                        viewModel.onTriggerEvent(MyBusinessEvent.LoadThreadsEvent)
                    }
                }
            }
        }
        viewState?.myThreads?.let { threads ->

            if (threads.isEmpty()) {

                Column(modifier = Modifier.fillMaxSize().padding(it)
                , horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,) {
                    Image(
                        painter = painterResource(id = R.drawable.empty_image),
                        contentDescription = ""
                    )
                    16.dp.Space()
                    Text(text = "No Trade")
                }

            }
            LazyColumn(modifier = Modifier.fillMaxWidth().padding(it)) {

                items(threads) { thread ->
                    var bg = if (thread.isArchived) Color(0xFFB6BCBE) else Color.White
                    ListItem(
                        colors = ListItemDefaults.colors(
                            containerColor = bg,
                        ),
                        leadingContent = {
                            AsyncImage(
                                model = thread.coverImage, contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(72.dp)
                                    .clip(RoundedCornerShape(16))
                            )
                        },

                        headlineContent = {
                            Text(
                                text = thread.title,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                        },
                        supportingContent = {
                            Text(
                                text = thread.theme,
                                fontSize = 12.sp,
                                color = Color.Gray,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                            )
                        },
                        trailingContent = {
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(
                                    imageVector = Icons.Outlined.Clear,
                                    contentDescription = "",
                                    tint = PrimaryColor,
                                )
                            }
                        })
                }

            }
        }
    }
}