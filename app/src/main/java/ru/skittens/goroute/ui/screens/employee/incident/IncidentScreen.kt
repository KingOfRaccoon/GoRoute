package ru.skittens.goroute.ui.screens.employee.incident

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import ru.skittens.goroute.ui.elements.BodyText
import ru.skittens.goroute.ui.elements.ButtonText
import ru.skittens.goroute.ui.elements.TitleText
import ru.skittens.goroute.ui.navigation.NavigationFun
import ru.skittens.goroute.ui.screens.employee.allincidents.AllIncidentsViewModel
import ru.skittens.goroute.ui.screens.start.onboarding.FilledColorButton
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
@Composable
fun IncidentScreen(navigateTo: NavigationFun, viewModel: AllIncidentsViewModel = koinInject()) {
    val incident by viewModel.currentIncident.collectAsState(null)

    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            BodyText(incident?.comment.orEmpty())
        }
        item {
            Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                ButtonText("Фотографии")
            }
        }
        item {
//            BoxWithConstraints(Modifier.fillMaxWidth()) {
//                LazyRow(
//                    Modifier
//                        .fillMaxWidth(),
//                    horizontalArrangement = Arrangement.spacedBy(12.dp)
//                ) {
//                    items(incident?.incidentPhotos.orEmpty()) {
//                        println("incidentPhotos: ${it.data.toByteArray()}")
//                        AsyncImage(
//                            it.data.encodeToByteArray(),
//                            null,
//                            Modifier
//                                .size(this@BoxWithConstraints.maxWidth / 5)
//                                .clip(RoundedCornerShape(12.dp)),
//                            contentScale = ContentScale.Crop
//                        )
//                    }
//                }
//            }
            HorizontalDivider(color = Color(0x1A000000), thickness = 1.dp)
        }
        item {
            Row(
                Modifier
                    .fillMaxWidth(),
            ) {
                TitleText("Подробности")
            }
        }
        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(19.dp, Alignment.Top),
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top)
                ) {
                    Row(
                        Modifier.fillMaxWidth(),
                        Arrangement.SpaceBetween,
                        Alignment.CenterVertically,
                    ) {
                        ButtonText(
                            text = "${incident?.latitude}, ${incident?.longitude}",
                            color = Color(0xff212121).copy(alpha = 0.5f),
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ButtonText(
                                text = "На карте",
                                color = Color(0xff01a451),
                                modifier = Modifier
                                    .wrapContentHeight(align = Alignment.CenterVertically)
                            )
                            Icon(
                                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                null,
                                Modifier.align(Alignment.CenterVertically),
                                MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    Row(
                        Modifier.fillMaxWidth(),
                        Arrangement.SpaceBetween,
                        Alignment.CenterVertically
                    ) {
                        ButtonText(
                            text = "Ответственный",
                            color = Color(0xff212121).copy(alpha = 0.5f),
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ButtonText(
                                text = "Нет",
                                color = Color(0xffff6060),
                                modifier = Modifier
                                    .wrapContentHeight(align = Alignment.CenterVertically)
                            )
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        ButtonText(
                            text = "Уровень угрозы",
                            color = Color(0xff212121).copy(alpha = 0.5f),
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ButtonText(
                                text = incident?.threatDegree?.name.orEmpty(),
                                color = Color(0xff212121),
                                modifier = Modifier
                                    .wrapContentHeight(align = Alignment.CenterVertically)
                            )
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        ButtonText(
                            text = "Источник",
                            color = Color(0xff212121).copy(alpha = 0.5f),
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ButtonText(
                                text = incident?.incidentType?.name.orEmpty(),
                                color = Color(0xff212121),
                                modifier = Modifier
                                    .wrapContentHeight(align = Alignment.CenterVertically)
                            )
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        ButtonText(
                            text = "Дата и время",
                            color = Color(0xff212121).copy(alpha = 0.5f),
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ButtonText(
                                text = incident?.date.orEmpty().split("T").first().split("-")
                                    .reversed().joinToString(".") { it },
                                color = Color(0xff212121),
                                modifier = Modifier
                                    .wrapContentHeight(align = Alignment.CenterVertically)
                            )
                        }
                    }
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TitleText(
                            text = "Ответственный",
                            color = Color(0xff212121),
                            modifier = Modifier
                                .wrapContentHeight(align = Alignment.CenterVertically)
                        )
                        ButtonText(
                            text = "Выбрать",
                            color = Color(0xff01a451),
                            textAlign = TextAlign.End,
                            modifier = Modifier
                                .wrapContentHeight(align = Alignment.CenterVertically)
                        )
                    }
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            TitleText(
                                text = "Ход решения",
                                color = Color(0xff212121),
                                modifier = Modifier
                                    .wrapContentHeight(align = Alignment.CenterVertically)
                            )
                            ButtonText(
                                text = "Добавить",
                                color = Color(0xff01a451),
                                textAlign = TextAlign.End,
                                modifier = Modifier
                                    .wrapContentHeight(align = Alignment.CenterVertically)
                            )
                        }
                    }
                    incident?.incidentStatus.orEmpty().forEach {
                        Row(
                            Modifier.fillMaxWidth(),
                            Arrangement.SpaceBetween,
                            Alignment.CenterVertically,
                        ) {
                            ButtonText(
                                text = it.date.split("T").first().split("-").reversed()
                                    .joinToString(".") { it },
                                color = Color(0xff212121).copy(alpha = 0.5f),
                            )
                            ButtonText(
                                text = it.title,
                                color = Color(0xff212121).copy(alpha = 0.5f),
                            )
                        }
                    }
                }
            }
        }
        item {
            FilledColorButton("Проблема решена", modifier = Modifier.fillMaxWidth()) {}
        }
    }
}