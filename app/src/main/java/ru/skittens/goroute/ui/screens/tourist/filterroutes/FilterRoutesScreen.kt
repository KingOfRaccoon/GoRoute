package ru.skittens.goroute.ui.screens.tourist.filterroutes

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import ru.skittens.goroute.ui.elements.ButtonText
import ru.skittens.goroute.ui.screens.tourist.map.ExpandCard

@Composable
fun FilterRoutesScreen(viewModel: FilterRoutesViewModel = koinInject()) {
    val seasonalityList by viewModel.seasonalityListFlow.collectAsState()
    val typeRouteList by viewModel.typeRouteListFlow.collectAsState()
    val conditionsList by viewModel.conditionsListFlow.collectAsState()
    val typeWayList by viewModel.typeWayListFlow.collectAsState()
    val difficultyList by viewModel.difficultyListFlow.collectAsState()
    val seasonality by viewModel.seasonalityFlow.collectAsState()
    val typeRoute by viewModel.typeRouteFlow.collectAsState()
    val conditions by viewModel.conditionsFlow.collectAsState()
    val typeWay by viewModel.typeWayFlow.collectAsState()
    val difficulty by viewModel.difficultyFlow.collectAsState()
    LazyColumn(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            FilterExpandCard("Сезонность", seasonalityList, seasonality, viewModel::setSeasonality)
        }

        item {
            FilterExpandCard("Тип маршрута", typeRouteList, typeRoute, viewModel::setTypeRoute)
        }

        item {
            FilterExpandCard("Условия посещения", conditionsList, conditions, viewModel::setConditions)
        }

        item {
            FilterExpandCard("Способ передвижения", typeWayList, typeWay, viewModel::setTypeWay)
        }

        item {
            FilterExpandCard("Сложность", difficultyList, difficulty, viewModel::setDifficulty)
        }
    }
}

@Composable
fun FilterExpandCard(
    title: String,
    allTags: List<String>,
    filter: String,
    setFilter: (String) -> Unit
) {
    ExpandCard({
        ButtonText(title)
    }) {
        Column {
            allTags.forEach { tag ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 32.dp, end = 8.dp),
                    Arrangement.SpaceBetween,
                    Alignment.CenterVertically
                ) {
                    ButtonText(tag)

                    AnimatedContent(targetState = tag == filter, label = title) {
                        IconButton(
                            onClick = { setFilter(tag) },
                            colors = IconButtonDefaults.iconButtonColors(Color.Transparent)
                        ) {
                            Icon(
                                if (it) Icons.Default.CheckBox else Icons.Default.CheckBoxOutlineBlank,
                                null,
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
            }
        }
    }
}