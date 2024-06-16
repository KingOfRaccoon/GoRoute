package ru.skittens.goroute.ui.screens.tourist.permission

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import org.koin.compose.koinInject
import ru.skittens.goroute.R
import ru.skittens.goroute.ui.elements.BodyText
import ru.skittens.goroute.ui.elements.ButtonText
import ru.skittens.goroute.ui.elements.CaptionText
import ru.skittens.goroute.ui.elements.TitleText
import ru.skittens.goroute.ui.screens.tourist.addincident.SelectVariantTextField
import ru.skittens.goroute.ui.screens.tourist.map.MapViewModel

@Composable
fun PermissionScreen() {
    Column(Modifier.padding(horizontal = 18.dp)) {
        Card(({  }),
            Modifier
                .fillMaxWidth()
                .aspectRatio(4f)
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(2f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.backgroung_onboarding),
                    null,
                    colorFilter = ColorFilter.tint(Color.White, blendMode = BlendMode.Softlight),
                    alpha = 0.4f,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = Color(0xFFFFF0C3),
                            shape = RoundedCornerShape(size = 24.dp)
                        )
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
                        Row(Modifier
                            .fillMaxWidth()) {
                            Image(
                                painter = painterResource(R.drawable.tree),
                                null,
                                Modifier.padding(top = 18.dp, start = 16.dp)
                            )
                            Spacer(Modifier.width(6.dp))
                            CaptionText("Природный парк Налычево", Modifier.padding(top = 18.dp), color = Color(0xFF01A451),)
                            Spacer(modifier = Modifier.weight(1f))
                            IconButton(onClick = {  },
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.edit),
                                    null,
                                )
                            }
                        }
                        TitleText("Центральный — Аагские нарзаны", textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 0.dp, start = 16.dp))
                    }
                    Spacer(Modifier.weight(1f))
                }
            }
        }
        Spacer(Modifier.height(12.dp))
        DropdownTextField("Тип разрешения")
        Spacer(Modifier.height(12.dp))
        OutLineTextFieldSample("Заголовоооок")
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutLineTextFieldSample(title: String) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    TextField(
        value = text,
        onValueChange = { text = it },
        label = { BodyText(text = title, textAlign = TextAlign.Center,) },
        shape = RoundedCornerShape(size = 24.dp),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 24.dp,
                spotColor = Color(0x0D000000),
                ambientColor = Color(0x0D000000)
            )
            .background(color = Color(0xFFFFF0C3), shape = RoundedCornerShape(size = 24.dp))
            .clip(RoundedCornerShape(size = 24.dp))
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownTextField(title: String){
    var expanded by remember { mutableStateOf(false) }
    val suggestions = listOf("Item1","Item2","Item3")
    var selectedText by remember { mutableStateOf("") }
    var textfieldSize by remember { mutableStateOf(Size.Zero)}
    val icon = if (expanded)
        painterResource(R.drawable.chevron_down)
    else
        painterResource(R.drawable.chevron_up)
    Box() {
        TextField(
            value = selectedText,
            onValueChange = { selectedText = it },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textfieldSize = coordinates.size.toSize()
                }
                .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 24.dp))
                .clip(RoundedCornerShape(size = 24.dp)),

            label = { BodyText(title, textAlign = TextAlign.Center,) },
            trailingIcon = {
                Icon(icon,"contentDescription",
                    Modifier.clickable { expanded = !expanded })
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current){textfieldSize.width.toDp()})
        ) {
            suggestions.forEach { label ->
                DropdownMenuItem(onClick = {
                    selectedText = label
                    expanded = false
                }, text = {
                    ButtonText(text = label)
                }, modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.surface).shadow(elevation = 24.dp, spotColor = Color(0x0D000000), ambientColor = Color(0x0D000000))
                    .clip(RoundedCornerShape(24.dp)))

            }
        }
    }
}
