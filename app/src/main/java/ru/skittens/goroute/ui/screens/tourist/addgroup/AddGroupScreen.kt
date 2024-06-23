package ru.skittens.goroute.ui.screens.tourist.addgroup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.PeopleOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import okhttp3.internal.wait
import ru.skittens.goroute.ui.elements.BodyText
import ru.skittens.goroute.ui.elements.ButtonText
import ru.skittens.goroute.ui.elements.TitleText
import ru.skittens.goroute.ui.navigation.Destinations
import ru.skittens.goroute.ui.navigation.NavigationFun
import ru.skittens.goroute.ui.screens.start.onboarding.FilledColorButton
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGroupScreen(navigateTo: NavigationFun) {
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 18.dp)
    ) {
        var nameGroup by remember { mutableStateOf(TextFieldValue("")) }
        TextField(
            value = nameGroup,
            maxLines = 1,
            singleLine = true,
            onValueChange = { nameGroup = it },
            label = { Text(text = "Название группы", textAlign = TextAlign.Center,) },
            leadingIcon = { Icon(Icons.Outlined.PeopleOutline, contentDescription = "Группв") },
            shape = RoundedCornerShape(size = 24.dp),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                focusedPrefixColor = MaterialTheme.colorScheme.primary,
                focusedSuffixColor = MaterialTheme.colorScheme.primary,
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
        Row(
            Modifier
                .fillMaxWidth()
                .padding(18.dp, 12.dp, 0.dp, 0.dp,),
        ) {
            TitleText("Участники")
        }
        Spacer(Modifier.height(8.dp))
        val suggestions = listOf("Индивидуальный поход","Групповой поход","Юридическое лицо", "dsaasd", "dss")
        SearchBox(suggestions, {})
        Spacer(Modifier.height(12.dp))
        Row(modifier = Modifier
            .padding(horizontal = 18.dp, vertical = 8.dp)
            .clickable { TODO("To profile") }) {
            Text(text = "1", color = Color(0x80212121),)
            Spacer(Modifier.width(12.dp))
            ButtonText(text = "Андрей Маслов")
            Spacer(Modifier.weight(1f))
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                null,
                Modifier.align(Alignment.CenterVertically),
                MaterialTheme.colorScheme.primary
            )
        }

        Spacer(Modifier.weight(1f))

        FilledColorButton(
            "Создать группу", modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp)
        ) { navigateTo(Destinations.Permission)}
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBox(items: List<String>, onClick: (String)->Unit){
    val sortedList = items.sortedBy { it }
//    OutlinedCard(Modifier.fillMaxWidth().background(Color.White).clip(RoundedCornerShape(24.dp)),
//        RoundedCornerShape(24.dp),
//        border = BorderStroke(0.dp, Color.Transparent)) {
        var searchList by remember { mutableStateOf(emptyList<String>()) }
        var searchQuery by remember { mutableStateOf("") }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 24.dp,
                    spotColor = Color(0x0D000000),
                    ambientColor = Color(0x0D000000)
                )
                .background(color = Color.White, shape = RoundedCornerShape(size = 24.dp))
                .clip(RoundedCornerShape(size = 24.dp)),
            shape = RoundedCornerShape(24.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF01A451),
                unfocusedBorderColor = Color.Transparent),
            leadingIcon = {Icon(Icons.Filled.Search, "Search")},
            maxLines = 1, singleLine = true, value = searchQuery, onValueChange = {
                searchQuery = it
                searchList = if (searchQuery.isBlank()) emptyList() else sortedList.filter { item -> item.lowercase(
                    Locale.getDefault()).contains(searchQuery.lowercase(Locale.getDefault())) }
            }, placeholder = { Text(text = "Поиск туристов") }
        )
        searchList.forEach{
            Column(modifier = Modifier.background(color = Color.White,  shape = RoundedCornerShape(size = 24.dp))){
                Text(it,
                    Modifier
                        .fillMaxWidth()
                        .clickable {
                            searchQuery = ""
                            searchList = emptyList()
                            onClick(it)
                        }
                        .padding(start = 18.dp, end = 18.dp, top = 12.dp, bottom = 12.dp))
            }
        }
//    }
}