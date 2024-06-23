package ru.skittens.goroute.ui.screens.employee.incident

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.koin.compose.koinInject
import ru.skittens.goroute.ui.elements.BodyText
import ru.skittens.goroute.ui.elements.ButtonText
import ru.skittens.goroute.ui.elements.TitleText
import ru.skittens.goroute.ui.navigation.NavigationFun
import ru.skittens.goroute.ui.screens.start.onboarding.CustomTextButton
import ru.skittens.goroute.ui.screens.start.onboarding.FilledColorButton
import ru.skittens.goroute.ui.screens.tourist.addincident.AddIncidentViewModel
import ru.skittens.goroute.ui.screens.tourist.map.MapViewModel

@Composable
fun IncidentScreen(navigateTo: NavigationFun, viewModel: MapViewModel = koinInject()) {
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { viewModel.photos.addAll(it) }
    )
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item{
            BodyText("Между домами 35/1 по пр. Циолковского и 5/2 по ул. Звездная площадка для накопления твердых коммунальных отходов отсутствует, на данной территории организован свалочный очаг, территория захламлена отходами.")
        }
        item {
            Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                ButtonText("Фотографии")
            }
        }
        item {
            BoxWithConstraints(Modifier.fillMaxWidth()) {
                LazyRow(
                    Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(viewModel.photos) {
                        AsyncImage(
                            it,
                            null,
                            Modifier
                                .size(this@BoxWithConstraints.maxWidth / 5)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
            HorizontalDivider(color = Color(0x1A000000), thickness = 1.dp)
        }
        item {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 0.dp, 0.dp),
            ) {
                TitleText("Подробности")
            }
        }
        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(19.dp, Alignment.Top),
                modifier = Modifier
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        ButtonText(
                            text = "53.57234,158.83971",
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
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
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
                                text = "Высокий",
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
                                text = "Спутник",
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
                                text = "12.03.2023 13:34",
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
                        modifier = Modifier
                            .fillMaxWidth()
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
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        ButtonText(
                            text = "12.03.2023",
                            color = Color(0xff212121).copy(alpha = 0.5f),
                        )
                        ButtonText(
                            text = "Проведено выездное обследование",
                            color = Color(0xff212121).copy(alpha = 0.5f),
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        ButtonText(
                            text = "12.03.2023",
                            color = Color(0xff212121).copy(alpha = 0.5f),
                        )
                        ButtonText(
                            text = "Управляющей компании направлено предостережение о недопустимости нарушений обязательных требований",
                            color = Color(0xff212121).copy(alpha = 0.5f),
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        ButtonText(
                            text = "12.03.2023",
                            color = Color(0xff212121).copy(alpha = 0.5f),
                        )
                        ButtonText(
                            text = "Управляющей компании направлено предостережение о недопустимости нарушений обязательных требований",
                            color = Color(0xff212121).copy(alpha = 0.5f),
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }
            }
        }
        item {
            FilledColorButton("Проблема решена", modifier = Modifier.fillMaxWidth()) {}
        }
    }
}