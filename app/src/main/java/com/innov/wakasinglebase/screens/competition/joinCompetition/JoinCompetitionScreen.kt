package com.innov.wakasinglebase.screens.competition.joinCompetition

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.innov.wakasinglebase.R
import com.innov.wakasinglebase.common.CustomIconButton
import com.innov.wakasinglebase.core.extension.Space

@Composable
fun JoinCompetitionScreen(
    navController: NavController,
    id: String,
    viewModel: JoinCompetitionViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()
    val joinState by viewModel.joinState.collectAsState()

    var accept by rememberSaveable {
        mutableStateOf(true)
    }

    LaunchedEffect(key1 = true) {
        viewModel.onTriggerEvent(JoinCompetitionEvent.OnLoadCompetitionEvent(id))
    }

    val scrollState = rememberScrollState()

    Scaffold {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            if (viewState?.isLoading == true) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth()
                )
                12.dp.Space()
                Text("charging ...",)
            }

            if (viewState?.error != null) {
                Text("${viewState?.error}")
            }
            if (viewState?.competition != null) {
                viewState?.competition?.let { competition ->
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White,
                        ),
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)
                    ) {
                        AsyncImage(
                            model = competition.banner, contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .height(240.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8))
                                //.padding(10.dp)
                        )
                        ListItem(
                            overlineContent = {
                                Text(text = "Waka-Challenge")
                            },

                            headlineContent = {
                                Text(
                                    text = "${competition.name} ",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Medium,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,

                                    )
                            },
                            supportingContent = {
                                Text(
                                    text = "${competition.detail} ",
                                    fontSize = 14.sp,
                                    color = Color.Gray,
                                    maxLines = 5,
                                    overflow = TextOverflow.Ellipsis,
                                )
                            },

                            )
                        4.dp.Space()
                        Column(
                            modifier = Modifier.padding(horizontal = 24.dp)
                        ) {

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Checkbox(checked = accept, onCheckedChange = {
                                    accept = it
                                })
                                Text(
                                    text = "Do you accept to join this competition and respect all condition?",
                                    color = Color.Blue,
                                    fontSize = 12.sp
                                )
                            }
                            72.dp.Space()
                            CustomIconButton(
                                isEnable = accept,
                                buttonText = "Join us", icon = R.drawable.ic_add
                            ) {
                                if (accept) {
                                    viewModel.onTriggerEvent(
                                        JoinCompetitionEvent.OnSubmitJoinCompetitionEvent(
                                            competition.id
                                        )
                                    )
                                }
                            }
                        }

                    }
                }
            }

        }
    }
}