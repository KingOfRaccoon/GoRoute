package ru.skittens.goroute.ui.screens.tourist.addincident

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import org.koin.compose.koinInject
import ru.skittens.domain.entity.IncidentRequest
import ru.skittens.goroute.R
import ru.skittens.goroute.ui.elements.BodyText
import ru.skittens.goroute.ui.elements.ButtonText
import ru.skittens.goroute.ui.elements.CaptionText
import ru.skittens.goroute.ui.elements.TitleText
import ru.skittens.goroute.ui.screens.start.onboarding.CustomTextButton
import ru.skittens.goroute.ui.screens.start.onboarding.FilledColorButton
import ru.skittens.goroute.ui.screens.tourist.map.MapViewModel
import java.io.IOException

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AddIncidentScreen(
    navigateBack: () -> Unit,
    viewModel: MapViewModel = koinInject(),
    addIncidentViewModel: AddIncidentViewModel = koinInject()
) {
    val levels by viewModel.levelsFlow.collectAsState()
    val types by viewModel.typesFlow.collectAsState()
    val selectedLevel = viewModel.selectedLevelState
    val selectedType = viewModel.selectedTypeState
    val descriptionState = viewModel.descriptionState
    val context = LocalContext.current

    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { viewModel.photos.addAll(it) }
    )

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

    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 18.dp), Arrangement.spacedBy(12.dp)
    ) {
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
            value = viewModel.currentLocation?.let { "Широта: ${it.latitude}, долгота: ${it.longitude}" }
                ?: "Проверьте подключение к GPS",
            onValueChange = { },
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
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
            modifier = Modifier.fillMaxWidth(),
            label = {
                CaptionText(
                    "Описание происшествия",
                    color = MaterialTheme.colorScheme.primary
                )
            },
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
            CustomTextButton("Добавить", color = MaterialTheme.colorScheme.primary) {
                galleryLauncher.launch("image/*")
            }
        }

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

        Spacer(Modifier.weight(1f))
        FilledColorButton("Добавить", modifier = Modifier.fillMaxWidth()) {
            Toast.makeText(context, "Сообщение о происшествии отправлено", Toast.LENGTH_SHORT)
                .show()
            navigateBack()
            addIncidentViewModel.addIncident(
                IncidentRequest(
                    descriptionState.value,
                    viewModel.getType()?.id ?: 1,
                    viewModel.currentLocation?.latitude ?: 159.0,
                    viewModel.currentLocation?.longitude ?: 56.0,
                    viewModel.getLevel()?.id ?: 1,
                ),
                viewModel.photos.toList().firstOrNull()?.let { readBytes(context, it) }
                    ?: ByteArray(0)
            )
        }
    }
}

@Throws(IOException::class)
private fun readBytes(context: Context, uri: Uri): ByteArray? =
    context.contentResolver.openInputStream(uri)?.use { it.buffered().readBytes() }

@Composable
fun SelectVariantTextField(
    label: String,
    selectedText: String,
    updateText: (String) -> Unit,
    suggestions: List<String>
) {
    var expanded by remember { mutableStateOf(false) }
    val icon =
        if (expanded) painterResource(R.drawable.chevron_down) else painterResource(R.drawable.chevron_up)
    var textfieldSize by remember { mutableStateOf(Size.Zero) }
    Box(
        Modifier
            .background(
                color = Color(0xFFFFFFFF),
                shape = RoundedCornerShape(size = 24.dp)
            )
            .animateContentSize()
    ) {
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
                    shape = RoundedCornerShape(size = 24.dp)
                ),
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
                    .background(color = MaterialTheme.colorScheme.surface)
                    .shadow(
                        elevation = 24.dp,
                        spotColor = Color(0x0D000000),
                        ambientColor = Color(0x0D000000)
                    )
                    .clip(RoundedCornerShape(24.dp))
            ) {
                suggestions.forEach { label ->
                    DropdownMenuItem(onClick = {
                        updateText(label)
                        expanded = false
                    }, text = {
                        ButtonText(text = label)
                    }, modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.surface)
                        .shadow(
                            elevation = 24.dp,
                            spotColor = Color(0x0D000000),
                            ambientColor = Color(0x0D000000)
                        )
                        .clip(RoundedCornerShape(24.dp))
                    )
                }
            }
        }
    }
}