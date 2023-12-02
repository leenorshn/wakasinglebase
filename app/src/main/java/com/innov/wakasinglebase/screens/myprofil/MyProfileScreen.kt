package com.innov.wakasinglebase.screens.myprofil

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.innov.wakasinglebase.R
import com.innov.wakasinglebase.core.DestinationRoute
import com.innov.wakasinglebase.core.DestinationRoute.AUTH_ROUTE
import com.innov.wakasinglebase.core.DestinationRoute.RECHARGE_ROUTE
import com.innov.wakasinglebase.core.extension.MediumSpace
import com.innov.wakasinglebase.core.extension.Space
import com.innov.wakasinglebase.data.model.UserModel
import com.innov.wakasinglebase.ui.theme.*


/**
 * Created by innov Victor on 4/1/2023.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyProfileScreen(
    navController: NavController,
    settingViewModel: SettingViewModel = hiltViewModel()
) {
    val viewState by settingViewModel.viewState.collectAsState()
    val uiState by settingViewModel.uiState

    val scrollState = rememberScrollState()



    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text("My Account", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            }, actions = {
                TextButton(onClick = {
                    settingViewModel.onTriggerEvent(SettingEvent.OnLogout)
                    navController.navigate(AUTH_ROUTE)
                }) {
                    Text("Logout")
                }
            })
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WhiteLightDimBg)
                .padding(it)
                .verticalScroll(scrollState)
        ) {


            if (uiState.currentUser != null) {

                ListItem(
                    headlineContent = {
                        Text(
                            text = "@${uiState.currentUser?.name}",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.Black,
                                fontSize = 22.sp
                            )
                        )
                    },
                    leadingContent = {
                        val image= if(uiState.currentUser?.profilePic.isNullOrEmpty())
                            "https://d2y4y6koqmb0v7.cloudfront.net/profil.png"
                        else uiState.currentUser?.profilePic
                        AsyncImage(
                            model = image,
                            contentDescription = "image thumbnail",
                            modifier = Modifier
                                .size(64.dp)
                                //.background(GrayMainColor)
                                .clip(RoundedCornerShape(50)),
                            contentScale = ContentScale.Crop
                        )
                    },
                    supportingContent = {
                        Text(
                            text = "${uiState.currentUser?.phone ?: "non defini"}",
                            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
                        )
                    },
                    trailingContent = {
                        IconButton(
                            modifier = Modifier
                                .padding(8.dp)
                                .background(
                                    color = Color.Blue.copy(alpha = 0.1f),
                                    shape = RoundedCornerShape(32)
                                )
                                .clip(RoundedCornerShape(32)),
                            onClick = {
                                navController.navigate(DestinationRoute.EDIT_PROFILE)
                            }) {
                            Icon(Icons.Outlined.Edit, "Edit")
                        }
                    }
                )

//                    Spacer(modifier = Modifier.width(24.dp))
//
                MediumSpace()

                GroupedUiCardSection(item = "Account") {
                    if (uiState.currentUser?.isMonetizated == true) {
                        SettingItem(
                            onClickItem = { /*TODO*/ },
                            icon = R.drawable.dollar_24,
                            title = R.string.balance,
                            user = uiState.currentUser
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(32.dp)) {

                            AccountButton(
                                icon = R.drawable.withdraw_money,
                                // iconColor = Color.Red,
                                name = "Withdraw "
                            ) {

                            }
                            AccountButton(
                                icon = R.drawable.send_money,
                                //  iconColor = Color.Blue,
                                name = "Send "
                            ) {

                            }
                            AccountButton(
                                icon = R.drawable.recharge_money,
                                // iconColor = Color.Black,
                                name = "Recharge"
                            ) {
                                navController.navigate(RECHARGE_ROUTE)
                            }

                        }
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 32.dp)
                        )
                    } else {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            AccountButton(
                                icon = R.drawable.recharge_money,
                                // iconColor = Color.Black,
                                name = "Recharge"
                            ) {
                                navController.navigate(RECHARGE_ROUTE)
                            }
                            Column {
                                Text(text = "Balance", fontSize = 12.sp, color = Color.Gray)
                                10.dp.Space()
                                Text(
                                    text = "${uiState.currentUser?.balance} $",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = White,
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .background(
                                            color = MaterialTheme.colorScheme.primary,
                                            RoundedCornerShape(20)
                                        )
                                        .padding(horizontal = 22.dp, vertical = 4.dp)
                                )
                            }
                        }
                        12.dp.Space()
                        SettingItem(
                            onClickItem = {
                                 navController.navigate(DestinationRoute.MONETISATION_ROUTE)
                            },
                            icon = R.drawable.dollar_24,
                            title = R.string.monetisated,
                            user = null
                        )
                    }


                    SettingItem(
                        onClickItem = {

                            navController.navigate(
                                DestinationRoute.MY_VIDEO_ROUTE.replace(
                                    "{videoId}",
                                    uiState.currentUser?.uid!!
                                )
                            )
                        },
                        icon = R.drawable.ic_graph,
                        title = R.string.myvideo,
                        user = uiState.currentUser
                    )
                    if (uiState.currentUser?.isMonetizated == true) {
                        SettingItem(
                            onClickItem = {
                                navController.navigate(DestinationRoute.MY_BUSINESS_ROUTE)
                            },
                            icon = R.drawable.ic_dollar,
                            title = R.string.mybusiness,
                            user = uiState.currentUser
                        )
                    }

                }
                GroupedUiCardSection(item = "Support & About") {

                    SettingItem(
                        onClickItem = { /*TODO*/ },
                        icon = R.drawable.logo_tiktok_compose,
                        title = R.string.about,
                        user = null
                    )

                    SettingItem(
                        onClickItem = { /*TODO*/ },
                        icon = R.drawable.ic_cle,
                        title = R.string.privacy_policy,
                        user = null
                    )

                }

            } else {
                LinearProgressIndicator(
                    color = PrimaryColor,
                    modifier = Modifier.fillMaxWidth()
                )
            }



            22.dp.Space()
            Text(
                text = "v(@leenor)",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.fillMaxWidth(),
                color = SubTextColor
            )
            16.dp.Space()
        }
    }

}

@Composable
fun GroupedUiCardSection(item: String, items: @Composable () -> Unit) {
    Text(
        text = item,
        modifier = Modifier.padding(horizontal = 20.dp),
        color = SubTextColor,
        style = MaterialTheme.typography.labelMedium
    )
    8.dp.Space()
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        modifier = Modifier.padding(horizontal = 8.dp),
        shape = RoundedCornerShape(6.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(start = 18.dp, end = 16.dp)
                .padding(vertical = 8.dp)
        ) {
            items.invoke()
        }
    }
    MediumSpace()
}

private val expandedTitleHeight = 132.dp


@Composable
fun SettingItem(
    onClickItem: () -> Unit,
    icon: Int,
    user: UserModel?,
    title: Int
) {
    val c = if (title == R.string.monetisated) Color.Blue.copy(alpha = 0.9f) else Color.Black
    Row(
        modifier = Modifier
            .fillMaxWidth()
            //.background(color=c)
            .clickable { onClickItem.invoke() }
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(19.dp),
            tint = c
        )
        Text(
            text = stringResource(id = title),
            modifier = Modifier.weight(1f),
            color = c,
            style = MaterialTheme.typography.bodyMedium
        )
        if (title == R.string.balance) {
            Text(
                text = "${user?.balance} $",
                style = MaterialTheme.typography.labelMedium,
                color = White,
                fontSize = 16.sp,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        RoundedCornerShape(20)
                    )
                    .padding(horizontal = 22.dp, vertical = 4.dp)
            )
        } else {
            Icon(
                painter = painterResource(id = R.drawable.ic_left_arrow),
                contentDescription = null,
                tint = c
            )
        }
    }

}

@Composable
fun AccountButton(
    icon: Int,
    // iconColor: Color,
    name: String,
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable {
            onClick.invoke()
        }) {
        OutlinedButton(
            onClick = { onClick.invoke() },
            contentPadding = PaddingValues(0.dp),
            shape = RoundedCornerShape(32),
            modifier = Modifier.size(56.dp),
        ) {
            Icon(painter = painterResource(id = icon), contentDescription = "", tint = Color.Black)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = name, style = TextStyle(color = Color.Black))
    }
}