package ru.skittens.goroute.ui.screens.start.splash

import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import org.koin.compose.koinInject
import ru.kingofraccoons.domain.util.Resource
import ru.skittens.goroute.R
import ru.skittens.goroute.ui.elements.BodyText
import ru.skittens.goroute.ui.elements.TitleText
import ru.skittens.goroute.ui.navigation.Destinations
import ru.skittens.goroute.ui.navigation.NavigationFun
import ru.skittens.goroute.ui.screens.start.AuthenticationViewModel
import ru.skittens.goroute.ui.screens.start.onboarding.drawableToBitmap

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplashScreen(
    navigateTo: NavigationFun,
    displayMetrics: DpSize = koinInject(),
    viewModel: AuthenticationViewModel = koinInject()
) {
    val user by viewModel.user.collectAsState()
    var fractionWidth by remember { mutableFloatStateOf(0.1f) }

    val context = LocalContext.current

    val width = displayMetrics.width
    val height = displayMetrics.height
    val imageDrawable =
        remember { ContextCompat.getDrawable(context, R.drawable.backgroung_onboarding) }
    val bitmap = remember { drawableToBitmap(imageDrawable!!) }
    val imageBitmap = bitmap.asImageBitmap()

    LaunchedEffect(Unit) {
        animate(0.1f, 1f, animationSpec = tween(1500)) { value, _ ->
            fractionWidth = value
        }
    }

    LaunchedEffect(Unit){
        viewModel.authenticationUserOnToken()
    }

    LaunchedEffect(fractionWidth == 1f) {
        if (fractionWidth == 1f) {
            if (viewModel.getToken().isNullOrEmpty())
                navigateTo(Destinations.OnBoarding)
            else
                if (user is Resource.Success)
                    when(user.data?.role){
                        "USER" -> navigateTo(Destinations.MainTourist)
                        "ADMIN" -> navigateTo(Destinations.MainAdmin)
                        "MODERATOR" -> navigateTo(Destinations.MainEmployee)
                        else -> navigateTo(Destinations.MainTourist)
                    }
                else if (user is Resource.Error)
                    navigateTo(Destinations.Authentication)
            navigateTo(Destinations.OnBoarding)
        }
    }

    Scaffold(
        Modifier.fillMaxSize().drawBehind {
            val translationFactor = 0.3f // 15% shift per each page
            val translationX = 4 * size.width * translationFactor
            drawIntoCanvas { canvas ->
                canvas.drawImageRect(
                    image = imageBitmap,
                    srcOffset = IntOffset(translationX.toInt(), 0),
                    dstSize = IntSize(
                        width.value.toInt() * (imageDrawable?.intrinsicWidth
                            ?: 1) / (imageDrawable?.intrinsicHeight ?: 1) * 2,
                        height.value.toInt()
                    ),
                    paint = Paint().apply {
                        isAntiAlias = true
                    }
                )
            }
        },
        containerColor = Color.White.copy(.5f),
        topBar = {
            TopAppBar(
                {},
                Modifier.systemBarsPadding(),
                colors = TopAppBarDefaults.topAppBarColors(Color.Transparent)
            )
        }) {
        Column(
            Modifier.fillMaxSize().padding(it),
            Arrangement.SpaceAround,
            Alignment.CenterHorizontally
        ) {
            Image(
                painterResource(R.drawable.logo_big),
                null,
                Modifier.fillMaxWidth(0.6f).aspectRatio(1f).scale(fractionWidth)
            )

            HalfTransparentCard(
                "Сделай путешествие еще более захватывающим и удобным!",
                titleAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun HalfTransparentCard(
    title: String,
    subTitle: String? = null,
    titleAlign: TextAlign = TextAlign.Start
) {
    BlurredCard(
        Modifier.fillMaxWidth().padding(horizontal = 18.dp),
        shape = RoundedCornerShape(26.dp)
    ) {
        Column(
            Modifier.fillMaxWidth().padding(18.dp, 16.dp),
            Arrangement.spacedBy(12.dp)
        ) {
            TitleText(
                title,
                Modifier.fillMaxWidth(),
                titleAlign,
                MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (subTitle != null)
                BodyText(
                    subTitle,
                    Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
        }
    }
}

@Composable
fun BlurredCard(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(16.dp),
    blurRadius: Dp = 16.dp,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier) {
        Card(
            shape = shape,
            elevation = CardDefaults.elevatedCardElevation(0.dp),
            colors = CardDefaults.cardColors(Color.Transparent),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
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