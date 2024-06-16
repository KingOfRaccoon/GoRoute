package ru.skittens.goroute.ui.screens.tourist.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import ru.skittens.goroute.R
import ru.skittens.goroute.ui.elements.BodyText
import ru.skittens.goroute.ui.elements.ButtonText
import ru.skittens.goroute.ui.elements.TitleText

@Composable
fun ProfileScreen() {
    val scrollState = rememberScrollState()
    Column(
        Modifier
//            .background(Color.Black)0xFF01A451
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(vertical = 6.dp, horizontal = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Box(
            Modifier
                .fillMaxWidth(.35f)
                .aspectRatio(1f)) {
            Image(
                painterResource(R.drawable.user), null,
                Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
            )
            Row(
                Modifier
                    .background(
                        Color(0xFF86CBF0), shape = CircleShape
                    )
                    .padding(12.dp, 6.dp, 12.dp, 6.dp)
                    .align(Alignment.BottomCenter), verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painterResource(R.drawable.crown),
                    null,
                )
                Spacer(modifier = Modifier.width(4.dp))
                BodyText(text = "280xp", color = Color.Black)
            }
        }
        Spacer(Modifier.height(18.dp))
        TitleText(
            "Василий Петров",
            Modifier.fillMaxWidth(.8f),
            TextAlign.Center,
            Color.Black,
        )
        Row(
            Modifier
                .padding(top = 8.dp)
                .clickable {

                }) {
            ButtonText(
                "Звание:", Modifier.fillMaxWidth(.2f), TextAlign.Center, Color.Black
            )
            ButtonText(
                "Мышь", Modifier.fillMaxWidth(.2f), TextAlign.Center, Color(0xFF01A451)
            )
//            Image(
//                painterResource(R.drawable.chevron_down),
//                null,
//                Modifier
//                    .fillMaxSize(.1f)
//                    .padding(top = 2.dp)
//            )
        }
        Spacer(modifier = Modifier.weight(1.0f))
        Row(
            Modifier
                .clip(RoundedCornerShape(24.dp))
                .clickable {}
                .background(color = Color(0xFFFFFFFF))
                .padding(12.dp, 8.dp)
                .fillMaxSize()
                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                Icon(
                    Icons.Default.Groups,
                    null,
                    tint = Color.White,
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color(0xFF01A451))
                        .padding(all = 10.dp)
                )
            }
            TitleText(text = "Друзья", Modifier.padding(start = 12.dp))
            Spacer(modifier = Modifier.weight(1.0f))
        }
        Spacer(Modifier.height(12.dp))
        Row(
            Modifier
                .clip(RoundedCornerShape(24.dp))
                .clickable {}
                .background(color = Color(0xFFFFFFFF))
                .padding(12.dp, 8.dp)
                .fillMaxSize()
                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                Icon(
                    Icons.Default.EmojiEvents,
                    null,
                    tint = Color.White,
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color(0xFF01A451))
                        .padding(all = 10.dp)
                )
            }
            TitleText(text = "Достижения", Modifier.padding(start = 12.dp))
            Spacer(modifier = Modifier.weight(1.0f))
        }
        Spacer(Modifier.height(12.dp))
        Row(
            Modifier
                .clip(RoundedCornerShape(24.dp))
                .clickable {}
                .background(color = Color(0xFFFFFFFF))
                .padding(12.dp, 8.dp)
                .fillMaxSize()
                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                Icon(
                    Icons.AutoMirrored.Filled.Assignment,
                    null,
                    tint = Color.White,
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color(0xFF01A451))
                        .padding(all = 10.dp)
                )
            }
            TitleText(text = "Рейтинг", Modifier.padding(start = 12.dp))
            Spacer(modifier = Modifier.weight(1.0f))
        }
        Spacer(modifier = Modifier.weight(1.0f))
    }
}

fun getRank(rating: Int) = when(rating){
    in 0..299 -> "Мышь"
    in 300..599 -> "Белка"
    in 600..899 -> "Кот"
    in 900..1199 -> "Лиса"
    in 1200..1499 -> "Волк"
    in 1500..1799 -> "Медведь"
    in 1800..2099 -> "Орел"
    in 2100..2399 -> "Ягуар"
    in 2400..2699 -> "Носорог"
    in 2700..2999 -> "Крокодил"
    else -> "Лев"
}