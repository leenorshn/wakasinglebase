package com.innov.wakasinglebase.screens.threads

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.innov.wakasinglebase.ui.theme.PrimaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThreadsScreen(
    navController: NavController,
    viewModel: ThreadViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Threads") }, actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Outlined.Refresh, contentDescription = "")
                }
            })
        }
    ) {

        if (viewState?.isLoading == true) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                color = PrimaryColor
            )
        }

        if (viewState?.error != null) {
            Text(text = "${viewState?.error}")
        }
        if (viewState?.threads != null) {
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                //.padding(start = 16.dp)
            ) {
                item {
                    Divider(modifier = Modifier.padding(end = 16.dp))
                }
                viewState?.threads?.let {listThreads ->


                items(listThreads) { thread ->
                    Card(modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)) {
                        Column {
                            AsyncImage(
                                model = thread.coverImage,
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillParentMaxHeight(0.5f)
                                    .fillMaxWidth()
                            )
                            Column (modifier = Modifier.padding(12.dp)){
                                Text(
                                    text = thread.title,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Row (horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()){
                                    Text(text = "${thread.price} $")
                                    OutlinedButton(onClick = { }) {
                                        Text(text = "Check")
                                    }
                                }
                            }
                        }

                    }
                }
                }
            }
        }
    }
}