package ru.skittens.goroute.ui.screens.tourist.route

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.skittens.goroute.R
import ru.skittens.goroute.ui.elements.BodyText
import ru.skittens.goroute.ui.elements.ButtonText
import ru.skittens.goroute.ui.elements.CaptionText
import ru.skittens.goroute.ui.elements.TitleText
import ru.skittens.goroute.ui.navigation.Destinations
import ru.skittens.goroute.ui.navigation.NavigationFun
import ru.skittens.goroute.ui.screens.start.onboarding.FilledColorButton

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun RouteScreen(navigateTo: NavigationFun) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 18.dp)
    ) {
        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .weight(weight = 0.33f)
                        .clip(shape = RoundedCornerShape(12.dp))
                        .background(color = Color(0xff212121).copy(alpha = 0.03f))
                        .padding(
                            horizontal = 14.dp,
                            vertical = 8.dp
                        )
                ) {
                    CaptionText(
                        text = "Время",
                        color = Color(0xff212121).copy(alpha = 0.5f),
                        modifier = Modifier
                            .wrapContentHeight(align = Alignment.CenterVertically)
                    )
                    TitleText(
                        text = "1д. 13ч.",
                        modifier = Modifier
                            .wrapContentHeight(align = Alignment.CenterVertically)
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(weight = 0.33f)
                        .clip(shape = RoundedCornerShape(12.dp))
                        .background(color = Color(0xff212121).copy(alpha = 0.03f))
                        .padding(
                            horizontal = 14.dp,
                            vertical = 8.dp
                        )
                ) {
                    CaptionText(
                        text = "Расстояние",
                        color = Color(0xff212121).copy(alpha = 0.5f),
                        modifier = Modifier
                            .wrapContentHeight(align = Alignment.CenterVertically)
                    )
                    TitleText(
                        text = "76км",
                        modifier = Modifier
                            .wrapContentHeight(align = Alignment.CenterVertically)
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(weight = 0.33f)
                        .clip(shape = RoundedCornerShape(12.dp))
                        .background(color = Color(0xff212121).copy(alpha = 0.03f))
                        .padding(
                            horizontal = 14.dp,
                            vertical = 8.dp
                        )
                ) {
                    CaptionText(
                        text = "Высота",
                        color = Color(0xff212121).copy(alpha = 0.5f),
                        modifier = Modifier
                            .wrapContentHeight(align = Alignment.CenterVertically)
                    )
                    TitleText(
                        text = "230м",
                        modifier = Modifier
                            .wrapContentHeight(align = Alignment.CenterVertically)
                    )
                }
            }
        }
        item {
            HorizontalDivider(color = Color(0x1A000000), thickness = 1.dp)
        }
        item {
            TitleText(
                text = "Маршрут",
                color = Color(0xff212121),
                modifier = Modifier
                    .requiredWidth(width = 256.dp)
            )
        }
        item {
            Box(
                modifier = Modifier
                    .requiredWidth(width = 178.dp)
                    .requiredHeight(height = 120.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.route_start),
                        contentDescription = "Frame 218",
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier
                            .requiredSize(size = 32.dp)
                            .clip(shape = RoundedCornerShape(24.dp))
                            .padding(
                                horizontal = 2.dp,
                                vertical = 4.dp
                            )
                    )
                    ButtonText(
                        text = "Центральный",
                        color = Color(0xff212121),
                        modifier = Modifier
                            .wrapContentHeight(align = Alignment.CenterVertically)
                    )
                }
                Divider(
                    color = Color(0xff01a451),
                    modifier = Modifier
                        .align(alignment = Alignment.TopStart)
                        .offset(
                            x = 15.dp,
                            y = 32.dp
                        )
                        .requiredWidth(width = 60.dp)
                        .rotate(degrees = -90f)
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .align(alignment = Alignment.TopStart)
                        .offset(
                            x = 0.dp,
                            y = 88.dp
                        )
                ) {
                    TextField(
                        value = "",
                        onValueChange = {},
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color(0xff01a451)
                        ),
                        modifier = Modifier
                            .requiredSize(size = 32.dp)
                            .clip(shape = RoundedCornerShape(24.dp))
                            .padding(
                                horizontal = 2.dp,
                                vertical = 4.dp
                            )
                    )
                    ButtonText(
                        text = "Аагские нарзаны",
                        color = Color(0xff212121),
                        modifier = Modifier
                            .wrapContentHeight(align = Alignment.CenterVertically)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(22.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .align(alignment = Alignment.TopStart)
                        .offset(
                            x = 10.dp,
                            y = 50.dp
                        )
                        .requiredWidth(width = 141.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(
                            3.75.dp,
                            Alignment.CenterHorizontally
                        ),
                        verticalAlignment = Alignment.CenterVertically,
                        content = {},
                        modifier = Modifier
                            .requiredSize(size = 12.dp)
                            .clip(shape = RoundedCornerShape(9.dp))
                            .background(color = Color(0xff01a451))
                            .padding(
                                horizontal = 0.7499998807907104.dp,
                                vertical = 1.499999761581421.dp
                            )
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CaptionText(
                            text = "5 стоянок",
                            color = Color(0xff212121).copy(alpha = 0.5f),
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
            }
        }
        item {
            FilledColorButton(
                text = "Пойти в поход",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp)
            ) {
                navigateTo(Destinations.Permission)
            }
        }
        item {
            HorizontalDivider(color = Color(0x1A000000), thickness = 1.dp)
        }
        item {
            Column(
                Modifier.fillMaxWidth()
            ) {
                TitleText(
                    text = "Характеристики",
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(6.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable { }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.bsbarchartfill),
                        contentDescription = "BsBarChartFill",
                        modifier = Modifier
                            .requiredSize(size = 20.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Spacer(Modifier.width(8.dp))
                    ButtonText(
                        text = "Средняя загруженность маршрута",
                        modifier = Modifier
                            .wrapContentHeight(align = Alignment.CenterVertically)
                    )
                    Spacer(Modifier.weight(1f))
                    Icon(
                        Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        null,
                        Modifier.align(Alignment.CenterVertically),
                        MaterialTheme.colorScheme.primary
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable { }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.mdsignalwifistatusbar2bar),
                        contentDescription = "BsBarChartFill",
                        modifier = Modifier
                            .requiredSize(size = 20.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Spacer(Modifier.width(8.dp))
                    ButtonText(
                        text = "Нестабильная связь",
                        modifier = Modifier
                            .wrapContentHeight(align = Alignment.CenterVertically)
                    )
                    Spacer(Modifier.weight(1f))
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable { }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.iowater),
                    contentDescription = "BsBarChartFill",
                    modifier = Modifier
                        .requiredSize(size = 20.dp)
                        .align(Alignment.CenterVertically)
                )
                Spacer(Modifier.width(8.dp))
                ButtonText(
                    text = "Нет источников воды",
                    modifier = Modifier
                        .wrapContentHeight(align = Alignment.CenterVertically)
                )
                Spacer(Modifier.weight(1f))
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    null,
                    Modifier.align(Alignment.CenterVertically),
                    MaterialTheme.colorScheme.primary
                )
            }
        }
        item {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.Top
            ) {
                ChipItem("Легкий")
                ChipItem("Лето-осень")
                ChipItem("Некатегорийный")
                ChipItem("Пеший")
                ChipItem("Необходимо разрешение")
                ChipItem("Рекреационный сбор")
                ChipItem("Старше 10 лет")
            }
        }
        item {
            HorizontalDivider(color = Color(0x1A000000), thickness = 1.dp)
        }
        item {
            TitleText(
                text = "Список лидеров"
            )
            CaptionText(
                text = "23 групп(ы) достигли цели",
                color = Color(0xff01a451),
                modifier = Modifier
                    .wrapContentHeight(align = Alignment.CenterVertically)
            )
        }
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ButtonText(
                            text = "1",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .requiredWidth(width = 12.dp)
                                .wrapContentHeight(align = Alignment.CenterVertically)
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.Start),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .requiredSize(size = 32.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .align(alignment = Alignment.Center)
                                        .offset(
                                            x = 0.dp,
                                            y = 0.dp
                                        )
                                        .requiredSize(size = 32.dp)
                                        .clip(shape = RoundedCornerShape(18.285717010498047.dp))
                                        .background(color = Color(0xfffff0c3))
                                )
                                TitleText(
                                    text = "С",
                                    color = Color(0xffffd043),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .align(alignment = Alignment.Center)
                                        .offset(
                                            x = 0.142822265625.dp,
                                            y = 0.30908203125.dp
                                        )
                                        .wrapContentHeight(align = Alignment.CenterVertically)
                                )
                            }
                            ButtonText(
                                text = "Семья Больных",
                                modifier = Modifier
                                    .wrapContentHeight(align = Alignment.CenterVertically)
                            )
                        }
                    }
                    ButtonText(
                        text = "56ч",
                        textAlign = TextAlign.Center,
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
                    .padding(vertical = 6.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ButtonText(
                        text = "2",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .requiredWidth(width = 12.dp)
                            .wrapContentHeight(align = Alignment.CenterVertically)
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .requiredSize(size = 32.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .align(alignment = Alignment.Center)
                                    .offset(
                                        x = 0.dp,
                                        y = 0.dp
                                    )
                                    .requiredSize(size = 32.dp)
                                    .clip(shape = RoundedCornerShape(18.285717010498047.dp))
                                    .background(color = Color(0xffdcf2fe))
                            )
                            Text(
                                text = "М",
                                color = Color(0xff86cbf0),
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .align(alignment = Alignment.Center)
                                    .offset(
                                        x = 0.618896484375.dp,
                                        y = 0.309814453125.dp
                                    )
                                    .wrapContentHeight(align = Alignment.CenterVertically)
                            )
                        }
                        ButtonText(
                            text = "МегаСпортики",
                            modifier = Modifier
                                .wrapContentHeight(align = Alignment.CenterVertically)
                        )
                    }
                }
                Text(
                    text = "58ч",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .wrapContentHeight(align = Alignment.CenterVertically)
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "3",
                        color = Color(0xff212121),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .requiredWidth(width = 12.dp)
                            .wrapContentHeight(align = Alignment.CenterVertically)
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .requiredSize(size = 32.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .align(alignment = Alignment.Center)
                                    .offset(
                                        x = 0.dp,
                                        y = 0.dp
                                    )
                                    .requiredSize(size = 32.dp)
                                    .clip(shape = RoundedCornerShape(18.285717010498047.dp))
                                    .background(color = Color(0xfff1e1f7))
                            )
                            Text(
                                text = "М",
                                color = Color(0xffc972ff),
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .align(alignment = Alignment.Center)
                                    .offset(
                                        x = 0.618896484375.dp,
                                        y = 0.309814453125.dp
                                    )
                                    .wrapContentHeight(align = Alignment.CenterVertically)
                            )
                        }
                        Text(
                            text = "Масловы",
                            color = Color(0xff212121),
                            modifier = Modifier
                                .wrapContentHeight(align = Alignment.CenterVertically)
                        )
                    }
                }
                Text(
                    text = "50ч",
                    color = Color(0xff212121),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .wrapContentHeight(align = Alignment.CenterVertically)
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable { }
            ) {
                ButtonText(
                    text = "Весь список лидеров",
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
        item {
            HorizontalDivider(color = Color(0x1A000000), thickness = 1.dp)
        }
        item {
            TitleText(
                text = "Безопасность"
            )
        }
        item {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable { }
            ) {
                BodyText(
                    text = "Регистрации в службах МЧС",
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
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable { }
            ) {
                BodyText(
                    text = "Рекомендуемое снаряжение",
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
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable { }
            ) {
                BodyText(
                    text = "Оказания медицинской помощи",
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
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable { }
            ) {
                BodyText(
                    text = "Порядок действий в случае ЧС",
                    color = Color(0xff01a451),
                    modifier = Modifier
                        .requiredWidth(width = 255.dp)
                        .wrapContentHeight(align = Alignment.CenterVertically)
                )
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    null,
                    Modifier.align(Alignment.CenterVertically),
                    MaterialTheme.colorScheme.primary
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable { }
            ) {
                BodyText(
                    text = "Правила пребывания в Парке",
                    color = Color(0xff01a451),
                    modifier = Modifier
                        .requiredWidth(width = 255.dp)
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
    }
}

@Composable
fun ChipItem(text: String) {
    InputChip(
        label = {
            CaptionText(
                text = text,
                color = Color(0xff212121),
                modifier = Modifier
                    .wrapContentHeight(align = Alignment.CenterVertically)
            )
        },
        shape = RoundedCornerShape(50.dp),
        colors = InputChipDefaults.inputChipColors(
            containerColor = Color(0x08212121),
        ),
        selected = true,
        onClick = { })
}