package com.innov.wakasinglebase.screens.gift

//import com.innov.core.DestinationRoute.AUTHENTICATION_ROUTE
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.innov.wakasinglebase.common.TopBar
import com.innov.wakasinglebase.core.extension.LargeSpace
import com.innov.wakasinglebase.core.extension.MediumSpace
import com.innov.wakasinglebase.core.extension.SmallSpace
import com.innov.wakasinglebase.core.extension.Space
import com.innov.wakasinglebase.data.model.TemplateModel
import com.innov.wakasinglebase.ui.theme.PrimaryColor
import com.innov.wakasinglebase.ui.theme.SubTextColor
import kotlin.math.absoluteValue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityScreen(navController: NavController,
                    cameraMediaViewModel: GiftViewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        TopBar(
            navIcon = null,
            title = "Gift"
        )
    }) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            GiftScreen(navController,cameraMediaViewModel)
        }
    }
}


@Composable
fun GiftScreen(
    navController: NavController,
    viewModel: GiftViewModel
) {
   val viewState by viewModel.viewState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.onTriggerEvent(CommunityMediaEvent.EventFetchTemplate)
    }


    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

        if (viewState?.templates==null){
             CircularProgressIndicator(
                trackColor = PrimaryColor,
                 color = Color.White,
            )
        }else{
            Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                viewState?.templates?.let {
                    TemplatePager(it)
                }
            }
        }
        LargeSpace()
    }

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColumnScope.TemplatePager(templates: List<TemplateModel>) {
    val pagerState = rememberPagerState(
        0,0f
    ) { templates.size }

    val currentItem by remember {
        derivedStateOf {
            pagerState.settledPage
        }
    }

    Text(text = templates[currentItem].name, style = MaterialTheme.typography.displayMedium)
    6.dp.Space()
    Text(
        text = templates[currentItem].hint,
        style = MaterialTheme.typography.labelLarge,
        color = SubTextColor
    )
    MediumSpace()
    HorizontalPager(
        contentPadding = PaddingValues(horizontal = 64.dp),
        state = pagerState,
        modifier = Modifier.weight(1f)
    ) {
        SingleTemplateCard(page = it, pagerState = pagerState, item = templates[it])
    }
    SmallSpace()
    Text(
        text = "${currentItem.plus(1)}/${templates.size}",
        color = SubTextColor,
        style = MaterialTheme.typography.labelMedium
    )
    SmallSpace()
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SingleTemplateCard(
    page: Int,
    pagerState: PagerState,
    item: TemplateModel,
) {
    val pageOffset =
        ((pagerState.currentPage - page) + (pagerState.currentPageOffsetFraction)).absoluteValue
    Card(
        modifier = Modifier
            .graphicsLayer {
                lerp(
                    start = 0.82f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                ).also { scale ->
                    scaleX = scale
                    scaleY = scale
                }
            },
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            Modifier
                .blur(if (pagerState.settledPage == page) 0.dp else 60.dp)
        )
        {
            AsyncImage(
                model = item.mediaUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}