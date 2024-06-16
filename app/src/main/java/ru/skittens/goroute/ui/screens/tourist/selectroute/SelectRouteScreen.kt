package ru.skittens.goroute.ui.screens.tourist.selectroute

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import org.koin.compose.koinInject
import ru.kingofraccoons.domain.util.Resource
import ru.skittens.domain.entity.Route
import ru.skittens.goroute.R
import ru.skittens.goroute.ui.elements.BodyText
import ru.skittens.goroute.ui.elements.ButtonText
import ru.skittens.goroute.ui.elements.CaptionText
import ru.skittens.goroute.ui.elements.TitleText
import ru.skittens.goroute.ui.navigation.Destinations
import ru.skittens.goroute.ui.navigation.NavigationFun
import kotlin.math.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SelectRouteScreen(navigateTo: NavigationFun, selectRouteViewModel: SelectRouteViewModel = koinInject()) {
    val routes by selectRouteViewModel.routes.collectAsState(Resource.Loading())

    LazyColumn(Modifier.fillMaxSize(),
    ) {
        stickyHeader {
            Row(Modifier
                .fillMaxWidth()
                .padding(start = 12.dp)
                .background(Color(0xFFF9F9F9)),
                Arrangement.Center,
                Alignment.CenterVertically) {

                IconButton(onClick = {  }, Modifier.background(Color(0xFFF9F9F9))) {
                    Image(
                        painterResource(R.drawable.filters),
                        null,
                        Modifier.background(Color(0xFFF9F9F9))
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                BodyText("${routes.data?.size ?: 0} маршрутов", textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.weight(1.5f))
            }
        }

        items(routes.data.orEmpty(), key = { it.id }) { RouteItem(it) { navigateTo(Destinations.Permission) } }
    }
}

@Composable
fun RouteItem(route: Route, onClick: () -> Unit) {
    Card(
        onClick,
        Modifier
            .fillMaxWidth()
            .aspectRatio(1.5f)
            .padding(vertical = 6.dp, horizontal = 18.dp)
            .shadow(elevation = 24.dp, spotColor = Color(0x0D000000), ambientColor = Color(0x0D000000))
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .weight(2f)
        ) {
            Image(
                painterResource(R.drawable.backgroung_onboarding),
                null,
                Modifier.fillMaxSize(),
                Alignment.Center,
                ContentScale.Crop
            )

            Card(
                Modifier
                    .align(Alignment.TopEnd)
                    .padding(10.dp),
                shape = CircleShape,
                colors = CardDefaults.cardColors(Color(0xFF326AFB))
            ) {
                CaptionText("Семейный", Modifier.padding(10.dp), color = Color.White)
            }
        }

        Column(
            Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(18.dp, 10.dp),
            Arrangement.spacedBy(8.dp)
        ) {
            TitleText(route.name)
            Row(Modifier.fillMaxWidth(), Arrangement.spacedBy(4.dp)) {
                Icon(
                    painterResource(R.drawable.ic_routes),
                    null,
                    tint = MaterialTheme.colorScheme.primary
                )
                ButtonText("${route.length}км")
                Spacer(Modifier.width(12.dp))
                Icon(Icons.Default.Schedule, null, tint = MaterialTheme.colorScheme.primary)
                ButtonText("6-8 часов")
            }
        }
    }
}