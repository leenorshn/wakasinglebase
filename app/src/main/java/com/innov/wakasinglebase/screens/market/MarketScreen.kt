package com.innov.wakasinglebase.screens.market


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
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
                items(viewState?.tickets!!) { ticket ->

                    Card(modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)) {
                        Column {
                            AsyncImage(
                                model = ticket.image,
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillParentMaxHeight(0.7f)
                                    .fillMaxWidth()
                            )
                            Column (modifier = Modifier.padding(12.dp)){
                                Text(
                                    text = ticket.name,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Row (horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()){
                                    Text(text = "${ticket.price.toInt()} Fc")
                                    OutlinedButton(onClick = { }) {
                                        Text(text = "reserver")
                                    }
                                }
                            }
                        }

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





