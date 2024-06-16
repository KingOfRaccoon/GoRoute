package ru.skittens.goroute.ui.screens.tourist.newroute

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import ru.skittens.goroute.R
import ru.skittens.goroute.ui.elements.TitleText
import ru.skittens.goroute.ui.navigation.Destinations
import ru.skittens.goroute.ui.navigation.NavigationFun
import ru.skittens.goroute.ui.screens.tourist.map.MapViewModel

@Composable
fun NewRouteScreen(navigateTo: NavigationFun, viewModel: MapViewModel = koinInject()) {
    val areas by viewModel.areasFlow.collectAsState()
    Column(Modifier.fillMaxSize().padding(horizontal = 18.dp)) {
        TypeCard(R.drawable.backgroung_onboarding, areas.data?.size ?: 0){
            navigateTo(Destinations.SelectParkOrSight)
        }
    }
}

@Composable
fun TypeCard(@DrawableRes image: Int, count: Int, onClick: () -> Unit) {
    Card(
        onClick,
        Modifier.fillMaxWidth().aspectRatio(2f),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(Color.Transparent, Color.Transparent)
    ) {
        Box {
            Image(
                painterResource(image),
                null,
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
            )
            Box(Modifier.matchParentSize().background(Color.White.copy(.5f)))

            BlurredCircle(Modifier.align(Alignment.TopEnd).clip(CircleShape)) {
                TitleText(count.toString(), Modifier.padding(15.dp, 5.dp), TextAlign.Center)
            }

            Row(
                Modifier.fillMaxWidth().padding(18.dp).align(Alignment.BottomCenter),
                Arrangement.SpaceBetween,
                Alignment.CenterVertically
            ) {
                TitleText("Природные парки")
                Icon(
                    Icons.AutoMirrored.Filled.ArrowForwardIos,
                    null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Composable
fun BlurredCircle(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = CircleShape,
    blurRadius: Dp = 16.dp,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier) {
        Card(
            shape = shape,
            elevation = CardDefaults.elevatedCardElevation(0.dp),
            colors = CardDefaults.cardColors(Color.Transparent),
            modifier = Modifier.padding(10.dp)
        ) {
            Box {
                Box(
                    modifier = Modifier
                        .clip(shape)
                        .alpha(.9f)
                        .background(Color.White)
                        .blur(
                            radius = blurRadius,
                            edgeTreatment = BlurredEdgeTreatment.Unbounded
                        )
                ) {
                    Box(Modifier.alpha(0f)) {
                        content()
                    }
                }
                content()
            }
        }
    }
}