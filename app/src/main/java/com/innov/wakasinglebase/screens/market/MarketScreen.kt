package com.innov.wakasinglebase.screens.market

//import com.innov.core.DestinationRoute.AUTHENTICATION_ROUTE
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.innov.wakasinglebase.R
import com.innov.wakasinglebase.core.extension.LargeSpace
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
                .padding(it), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                if (viewState?.isLoading == true) {
                    LinearProgressIndicator(
                        trackColor = PrimaryColor,
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            if (viewState?.tickets != null) {
                items(viewState?.tickets!!) { ticket ->
                    Divider()
                    ListItem(
                        leadingContent = {
                            Box(
                                modifier = Modifier
                                    .size(64.dp)

                                    .clip(RoundedCornerShape(20))
                                    .background(color = Color(0xFFF7F1EF)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painterResource(id = R.drawable.dollar_alt_24),
                                    contentDescription = "",
                                    modifier = Modifier.padding(8.dp).size(40.dp)
                                )
                            }
                        },
                        headlineContent = { Text(text = ticket.name, fontSize = 20.sp, fontWeight = FontWeight.Medium) },
                        supportingContent = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = ticket.status + " ")
                                Text(text = "Fc ${ticket.price}")
                            }
                        },
                        trailingContent = {
                            Button(onClick = { /*TODO*/ }) {
                                Text(text = "Buy")
                            }
                        })

                }
            }
        }
        LargeSpace()

    }

}





