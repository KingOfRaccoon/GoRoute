package ru.skittens.goroute.ui.screens.tourist.permission

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRightAlt
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import org.koin.compose.koinInject
import ru.skittens.goroute.R
import ru.skittens.goroute.ui.elements.BodyText
import ru.skittens.goroute.ui.elements.ButtonText
import ru.skittens.goroute.ui.elements.CaptionText
import ru.skittens.goroute.ui.elements.TitleText
import ru.skittens.goroute.ui.navigation.Destinations
import ru.skittens.goroute.ui.navigation.NavigationFun
import ru.skittens.goroute.ui.screens.start.onboarding.CustomTextButton
import ru.skittens.goroute.ui.screens.start.onboarding.FilledColorButton
import ru.skittens.goroute.ui.screens.tourist.addincident.SelectVariantTextField
import ru.skittens.goroute.ui.screens.tourist.map.MapViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PermissionScreen(
    navigateTo: NavigationFun,
    popBackStack: () -> Unit,
    viewModel: MapViewModel = koinInject()
) {
    val route by viewModel.currentRoute.collectAsState(null)
    val selectedTypePermission by viewModel.typePermissionFlow.collectAsState()
    val typePermissionList by viewModel.typePermissionListFlow.collectAsState()
    val dateStart by viewModel.dateStartFlow.collectAsState()
    val dateEnd by viewModel.dateEndFlow.collectAsState()
    val selectedGroup by viewModel.groupFlow.collectAsState()
    val groupList by viewModel.groupListFlow.collectAsState()
    val selectedLeaderGroup by viewModel.leaderGroupFlow.collectAsState()
    val leaderGroupList by viewModel.leaderGroupListFlow.collectAsState()
    val selectedTypeWay by viewModel.typeWayFlow.collectAsState()
    val typeWayList by viewModel.typeWayListFlow.collectAsState()
    val selectedTargetVisit by viewModel.targetVisitFlow.collectAsState()
    val targetVisitList by viewModel.targetVisitListFlow.collectAsState()

    var isDateStart by remember { mutableStateOf(true) }
    var showDatePicker by remember { mutableStateOf(false) }

    if (showDatePicker) {
        MyDatePickerDialog(
            onDateSelected = {
                if (isDateStart)
                    viewModel.setDateStart(it)
                else
                    viewModel.setDateEnd(it)
            },
            onDismiss = { showDatePicker = false }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 18.dp)
    ) {
        Card(
            Modifier
                .fillMaxWidth()
                .aspectRatio(4f)
        ) {
            Box(Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.backgroung_onboarding),
                    null,
                    colorFilter = ColorFilter.tint(Color.White, blendMode = BlendMode.Softlight),
                    alpha = 0.4f,
                    modifier = Modifier
                        .fillMaxSize()
                        .shadow(
                            elevation = 24.dp,
                            spotColor = Color(0x0D000000),
                            ambientColor = Color(0x0D000000)
                        ),
                    alignment = Alignment.TopStart,
                    contentScale = ContentScale.Crop,
                )
                Column {
                    Column {
                        Row(
                            Modifier
                                .fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(R.drawable.tree),
                                null,
                                Modifier.padding(top = 18.dp, start = 16.dp)
                            )
                            Spacer(Modifier.width(6.dp))
                            CaptionText(
                                "Природный парк ${viewModel.topBarValue}",
                                Modifier.padding(top = 18.dp),
                                color = Color(0xFF01A451),
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            IconButton(
                                onClick = popBackStack,
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.edit),
                                    null,
                                )
                            }
                        }
                        TitleText(
                            route?.name.orEmpty(), textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 0.dp, start = 16.dp)
                        )
                    }
                    Spacer(Modifier.weight(1f))
                }
            }
        }
        Spacer(Modifier.height(12.dp))
        SelectVariantTextField(
            "Тип разрешения",
            selectedTypePermission,
            viewModel::setTypePermission,
            typePermissionList
        )
        Spacer(Modifier.height(12.dp))
        Row(
            Modifier.fillMaxWidth(),
            Arrangement.spacedBy(12.dp),
            Alignment.CenterVertically
        ) {
            DateField("Прибытие", dateStart) {
                isDateStart = true
                showDatePicker = true
            }

            Icon(
                Icons.AutoMirrored.Filled.ArrowRightAlt,
                null,
                tint = Color(0x80000000)
            )

            DateField("Отбытие", dateEnd) {
                isDateStart = false
                showDatePicker = true
            }
        }

        Spacer(Modifier.height(18.dp))
        HorizontalDivider(color = Color(0x1A000000), thickness = 1.dp)
        Spacer(Modifier.height(12.dp))

        Row(
            Modifier
                .fillMaxWidth()
                .padding(18.dp, 0.dp, 0.dp, 0.dp),
            Arrangement.SpaceBetween, Alignment.CenterVertically
        ) {
            TitleText("Группа")
            CustomTextButton("создать новую", color = MaterialTheme.colorScheme.primary) {
                navigateTo(Destinations.AddGroup)
            }
        }
        SelectVariantTextField(
            "Выберите группу",
            selectedGroup,
            viewModel::setGroup,
            groupList
        )

        Row(
            Modifier
                .fillMaxWidth()
                .padding(18.dp, 12.dp, 0.dp, 0.dp),
        ) {
            TitleText("Руководитель")
        }
        Spacer(Modifier.height(6.dp))
        SelectVariantTextField(
            "Выберите пользователя",
            selectedLeaderGroup,
            viewModel::setLeaderGroup,
            leaderGroupList
        )

        Spacer(Modifier.height(18.dp))
        HorizontalDivider(color = Color(0x1A000000), thickness = 1.dp)
        Spacer(Modifier.height(12.dp))

        Row(
            Modifier
                .fillMaxWidth()
                .padding(18.dp, 12.dp, 0.dp, 0.dp),
        ) {
            TitleText("Дополнительная информация")
        }
        Spacer(Modifier.height(12.dp))
        SelectVariantTextField(
            "Способ перемещения",
            selectedTypeWay,
            viewModel::setTypeWay,
            typeWayList
        )
        Spacer(Modifier.height(12.dp))
        SelectVariantTextField(
            "Цель посещения",
            selectedTargetVisit,
            viewModel::setTargetVisit,
            targetVisitList
        )

        Spacer(Modifier.height(24.dp))

        FilledColorButton("Отправить заявку", modifier = Modifier.fillMaxWidth()) {

        }
    }
}

@Composable
fun RowScope.DateField(title: String, value: String, onClick: () -> Unit) {
    Card(
        onClick,
        Modifier.weight(1f),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp)
                .minimumInteractiveComponentSize()
                .animateContentSize(),
            Arrangement.spacedBy(8.dp),
            Alignment.CenterVertically
        ) {
            Icon(Icons.Outlined.CalendarMonth, contentDescription = "Календарь")
            Column(verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically)) {
                CaptionText(
                    text = title,
                    color = if (value.isEmpty())
                        MaterialTheme.colorScheme.onBackground
                    else
                        MaterialTheme.colorScheme.primary
                )

                if (value.isNotEmpty())
                    ButtonText(text = value)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePickerDialog(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return utcTimeMillis <= System.currentTimeMillis()
        }
    })

    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = {
                onDateSelected(selectedDate)
                onDismiss()
            }

            ) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            Button(onClick = {
                onDismiss()
            }) {
                Text(text = "Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

private fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}