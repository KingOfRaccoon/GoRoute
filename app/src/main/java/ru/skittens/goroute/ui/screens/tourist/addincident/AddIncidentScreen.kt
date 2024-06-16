package ru.skittens.goroute.ui.screens.tourist.addincident

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import org.koin.compose.koinInject
import ru.skittens.goroute.R
import ru.skittens.goroute.ui.elements.BodyText
import ru.skittens.goroute.ui.elements.ButtonText
import ru.skittens.goroute.ui.elements.CaptionText
import ru.skittens.goroute.ui.elements.TitleText
import ru.skittens.goroute.ui.screens.start.onboarding.CustomTextButton
import ru.skittens.goroute.ui.screens.tourist.map.MapViewModel

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AddIncidentScreen(viewModel: MapViewModel = koinInject()) {
    val levels by viewModel.levelsFlow.collectAsState()
    val types by viewModel.typesFlow.collectAsState()
    val selectedLevel = viewModel.selectedLevelState
    val selectedType = viewModel.selectedTypeState
    val descriptionState = viewModel.descriptionState

    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

//1. when the app get launched for the first time
    LaunchedEffect(Unit) {
        locationPermissions.launchMultiplePermissionRequest()
    }

    LaunchedEffect(Unit) {
        viewModel.getCurrentLocation()
    }

    LaunchedEffect(Unit) {
        viewModel.loadLevels()
    }

    LaunchedEffect(Unit) {
        viewModel.loadTypes()
    }

    println("latitude: ${viewModel.currentLocation?.latitude}: longitude: ${viewModel.currentLocation?.longitude}")

    Column(Modifier.fillMaxSize(), Arrangement.spacedBy(12.dp)) {
        TitleText(
            "Сообщить о проблеме",
            Modifier
                .fillMaxWidth()
                .padding(16.dp), TextAlign.Center
        )

        SelectVariantTextField(
            "Опастность происшествия",
            selectedLevel.value,
            selectedLevel::value::set,
            levels.data?.map { it.name }.orEmpty()
        )

        TextField(
            value = viewModel.currentLocation?.let { "Широта: ${it.latitude}, долгота: ${it.longitude}"} ?: "Проверьте подключение к GPS",
            onValueChange = { },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            label = { BodyText("Геолокация", color = MaterialTheme.colorScheme.primary) },
            colors = TextFieldDefaults.colors(
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(24.dp)
        )

        SelectVariantTextField(
            "Тип происшествия",
            selectedType.value,
            selectedType::value::set,
            types.data?.map { it.name }.orEmpty()
        )

        TextField(
            value = descriptionState.value,
            onValueChange = descriptionState::value::set,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp),
            label = { CaptionText("Описание происшествия", color = MaterialTheme.colorScheme.primary) },
            colors = TextFieldDefaults.colors(
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(24.dp)
        )

        Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
            ButtonText("Фотографии")
            CustomTextButton("Добавить"){
                
            }
        }
    }
}

@Composable
fun SelectVariantTextField(
    label: String,
    selectedText: String,
    updateText: (String) -> Unit,
    suggestions: List<String>
) {
    var expanded by remember { mutableStateOf(false) }
    val icon = if (expanded) painterResource(R.drawable.chevron_down) else painterResource(R.drawable.chevron_up)
    var textfieldSize by remember { mutableStateOf(Size.Zero) }
    Box(Modifier
        .padding(horizontal = 18.dp)
        .background(
            color = Color(0xFFFFFFFF),
            shape = RoundedCornerShape(size = 24.dp)
    )) {
        TextField(
            value = selectedText,
            onValueChange = { },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .onGloballyPositioned { coordinates ->
                    textfieldSize = coordinates.size.toSize()
                }
                .background(
                    color = Color(0xFFFFFFFF),
                    shape = RoundedCornerShape(size = 24.dp)),
            label = { BodyText(label, color = MaterialTheme.colorScheme.primary) },
            trailingIcon = {
                Icon(
                    icon,
                    null,
                    Modifier.clickable { expanded = !expanded })
            },
            colors = TextFieldDefaults.colors(
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(24.dp)

        )
        MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(24.dp))) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
                    .background(color = MaterialTheme.colorScheme.surface).shadow(elevation = 24.dp, spotColor = Color(0x0D000000), ambientColor = Color(0x0D000000))
                    .clip(RoundedCornerShape(24.dp))
            ) {
                suggestions.forEach { label ->
                    DropdownMenuItem(onClick = {
                        updateText(label)
                        expanded = false
                    }, text = {
                        ButtonText(text = label)
                    }, modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.surface).shadow(elevation = 24.dp, spotColor = Color(0x0D000000), ambientColor = Color(0x0D000000))
                        .clip(RoundedCornerShape(24.dp)))
                }
        }}
    }
}