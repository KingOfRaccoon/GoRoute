package ru.skittens.goroute.ui.screens.tourist.routes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.skittens.goroute.ui.elements.BigTitleText
import ru.skittens.goroute.ui.elements.ButtonText
import ru.skittens.goroute.ui.elements.TitleText
import ru.skittens.goroute.ui.navigation.Destinations
import ru.skittens.goroute.ui.navigation.NavigationFun
import ru.skittens.goroute.ui.screens.start.onboarding.CustomTextButton

@Composable
fun RoutesScreen(navigateTo: NavigationFun) {
    LazyColumn(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            BigTitleText("Походы", Modifier.padding(horizontal = 36.dp))
        }

        item {
            Card(
                { navigateTo(Destinations.NewRoute) },
                Modifier.padding(horizontal = 18.dp),
                shape = RoundedCornerShape(24.dp)
            ) {
                Box(Modifier.fillMaxWidth().aspectRatio(2.5f).background(Color.LightGray)) {
                    BlurredChip(Modifier.align(Alignment.Center)) {
                        TextButton({ navigateTo(Destinations.NewRoute) }) {
                            Icon(
                                Icons.Default.Add,
                                null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(Modifier.width(10.dp))
                            ButtonText(
                                "Создать поход",
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }

        item {
            NavigateCard({ navigateTo(Destinations.EndedRoutes) }) {
                TitleText("Завершенные походы")
            }
        }

        item {
            Row(
                Modifier.fillMaxWidth().padding(horizontal = 36.dp),
                Arrangement.SpaceBetween,
                Alignment.CenterVertically
            ) {
                BigTitleText("Группы")
                CustomTextButton("Создать", color = MaterialTheme.colorScheme.primary) {
                    navigateTo(Destinations.AddGroup)
                }
            }
        }

        items(listOf("Семья Больных", "МегаСпортики")) {
            GroupCard(it){
                navigateTo(Destinations.Group)
            }
        }
    }
}

@Composable
fun GroupCard(nameGroup: String, onClick: () -> Unit) {
    NavigateCard(onClick) {
        Box(
            Modifier
                .fillMaxWidth(.1f)
                .aspectRatio(1f)
                .clip(CircleShape)
                .background(Color(0xFFFFF0C3)),
            Alignment.Center
        ) {
            BigTitleText(nameGroup.first().toString(), color = Color(0xFFFFD043))
        }
        Spacer(Modifier.width(12.dp))
        ButtonText(nameGroup)
    }
}

@Composable
fun NavigateCard(onClick: () -> Unit, content: @Composable RowScope.() -> Unit) {
    Card(
        onClick,
        Modifier.fillMaxWidth()
            .shadow(elevation = 24.dp, spotColor = Color(0x0D000000), ambientColor = Color(0x0D000000))
            .padding(horizontal = 18.dp),
        shape = RoundedCornerShape(24.dp),
    ) {
        Row(
            Modifier.fillMaxWidth().padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            content()
            Spacer(Modifier.weight(1f))
            Icon(Icons.AutoMirrored.Filled.ArrowForwardIos, null)
        }
    }
}

@Composable
fun BlurredChip(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = CircleShape,
    blurRadius: Dp = 16.dp,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier) {
        Card(
            shape = shape,
            elevation = CardDefaults.elevatedCardElevation(0.dp),
            colors = CardDefaults.cardColors(Color.Transparent),
            modifier = Modifier.padding(10.dp)
        ) {
            Box {
                Box(
                    modifier = Modifier
                        .clip(shape)
                        .alpha(.9f)
                        .background(Color.White)
                        .blur(
                            radius = blurRadius,
                            edgeTreatment = BlurredEdgeTreatment.Unbounded
                        )
                ) {
                    Box(Modifier.alpha(0f)) {
                        content()
                    }
                }
                content()
            }
        }
    }
}