package com.innov.wakasinglebase.screens.myprofil

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.innov.wakasinglebase.R
import com.innov.wakasinglebase.common.TopBar
import com.innov.wakasinglebase.core.DestinationRoute.AUTHENTICATION_ROUTE
import com.innov.wakasinglebase.core.extension.MediumSpace
import com.innov.wakasinglebase.core.extension.SmallSpace
import com.innov.wakasinglebase.core.extension.Space
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

//    val isCollapsed: Boolean by remember {
//        derivedStateOf {
//            scrollState.value.dp > expandedTitleHeight
//        }
//    }

    Scaffold(
        topBar = { TopBar(title =  stringResource(id = R.string.settings_and_privacy)) { navController.navigateUp() } }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(WhiteLightDimBg)
                .padding(it)
                .verticalScroll(scrollState)
        ) {

            SmallSpace()
            if(uiState.currentUser!=null){
                ListItem(
                    headlineContent = {
                        Text(
                        text = "@${uiState.currentUser?.name}",
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black, fontSize = 22.sp)
                    )
                    },
                    leadingContent = {
                        AsyncImage(
                            model = uiState.currentUser?.profilePic,
                            contentDescription = "image thumbnail",
                            modifier = Modifier
                                .size(64.dp)
                                .background(GrayMainColor)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    },
                    supportingContent =  {
                        Text(
                            text = "${uiState.currentUser?.phone?:"non defini"}",
                            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
                        )
                    },
                    trailingContent = {
                        Text(
                            text = "${uiState.currentUser?.balance} fc",
                            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Blue)
                        )
                    }
                )

//                    Spacer(modifier = Modifier.width(24.dp))
//




            }else{
                CircularProgressIndicator(color= PrimaryColor)
            }
            MediumSpace()

            viewState?.settingUiData?.let {
                it.forEach {
                    GroupedUiCardSection(item = it.key ,items= it.value, onClickItem = { titleId ->
                        when (titleId) {
                            R.string.my_account -> navController.navigate(AUTHENTICATION_ROUTE)
                        }
                    })
                }
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
fun GroupedUiCardSection(item: String,items: List<RowItem>, onClickItem: (Int) -> Unit) {
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
            items.forEach {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onClickItem(it.title) }
                        .padding(vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        painter = painterResource(id = it.icon),
                        contentDescription = null,
                        modifier = Modifier.size(19.dp),
                        tint = Gray
                    )
                    Text(
                        text = stringResource(id = it.title),
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    if (it.title == R.string.my_account) {
                        Text(
                            text = stringResource(id = R.string.sign_up),
                            style = MaterialTheme.typography.labelMedium,
                            color = White,
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colorScheme.primary,
                                    RoundedCornerShape(2.dp)
                                )
                                .padding(horizontal = 22.dp, vertical = 4.dp)
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_left_arrow),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                    }
                }
            }
        }
    }
    MediumSpace()
}

private val expandedTitleHeight = 132.dp