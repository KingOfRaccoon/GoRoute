package ru.skittens.goroute.ui.screens.employee.allincidents

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import ru.skittens.goroute.ui.elements.BodyText
import ru.skittens.goroute.ui.elements.ButtonText
import ru.skittens.goroute.ui.elements.CaptionText
import ru.skittens.goroute.ui.elements.TitleText
import ru.skittens.goroute.ui.navigation.Destinations
import ru.skittens.goroute.ui.navigation.NavigationFun

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AllIncidentsScreen(navigateTo: NavigationFun, viewModel: AllIncidentsViewModel = koinInject(), ) {
    val incidents by viewModel.incidents.collectAsState()

    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        stickyHeader {
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF9F9F9))
                    .padding(horizontal = 18.dp, vertical = 4.dp),
                Arrangement.spacedBy(8.dp),
                Alignment.CenterVertically
            ) {
                TitleText("Новые заявки")
                Box(Modifier.background(Color(0xFFFF6060), CircleShape)) {
                    TitleText((incidents.data?.size ?: 0).toString(), Modifier.padding(horizontal = 8.dp), color = Color.White)
                }
            }
        }

        items(incidents.data.orEmpty()) {
            Card(Modifier.fillMaxWidth().clickable {
                viewModel.setSelectedId(it.id)
                navigateTo(Destinations.Incident)
            }) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 12.dp), Arrangement.spacedBy(6.dp)
                ) {
                    Row(Modifier.fillMaxWidth(), Arrangement.spacedBy(6.dp), Alignment.CenterVertically) {
                        Icon(Icons.Default.LocalFireDepartment, null, tint = Color(0xFFFFA133))
                        ButtonText("Пожар")
                        Spacer(Modifier.weight(1f))
                        Card(
                            Modifier,
                            shape = CircleShape,
                            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
                        ) {
                            CaptionText("Турист", Modifier.padding(10.dp, 4.dp), color = Color.White)
                        }
                    }
                    BodyText(it.comment)
                    Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                        CaptionText("Нет ответственного", color = MaterialTheme.colorScheme.onBackground.copy(.5f))
                        CaptionText("12.03.2023 13:54", color = MaterialTheme.colorScheme.onBackground.copy(.5f))
                    }
                }
            }
        }

        stickyHeader {
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF9F9F9))
                    .padding(horizontal = 18.dp, vertical = 4.dp),
                Arrangement.spacedBy(8.dp),
                Alignment.CenterVertically
            ) {
                TitleText(
                    "В обработке",
                    Modifier
                        .fillMaxWidth()
                )
            }
        }

        item {
            Card(Modifier.fillMaxWidth()) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 12.dp), Arrangement.spacedBy(6.dp)
                ) {
                    Row(Modifier.fillMaxWidth(), Arrangement.spacedBy(6.dp), Alignment.CenterVertically) {
                        Icon(Icons.Default.LocalFireDepartment, null, tint = Color(0xFFFFA133))
                        ButtonText("Пожар")
                        Spacer(Modifier.weight(1f))
                        Card(
                            Modifier,
                            shape = CircleShape,
                            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
                        ) {
                            CaptionText("Турист", Modifier.padding(10.dp, 4.dp), color = Color.White)
                        }
                    }
                    BodyText("Между домами 35/1 по пр. Циолковского и 5/2 по ул. Звездная площадка для накопления твердых коммунальных отходов отсутствует, на данной территории организован свалочный очаг, территория захламлена отходами.")
                    Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                        CaptionText("Нет ответственного", color = MaterialTheme.colorScheme.onBackground.copy(.5f))
                        CaptionText("12.03.2023 13:54", color = MaterialTheme.colorScheme.onBackground.copy(.5f))
                    }
                }
            }
        }
    }
}