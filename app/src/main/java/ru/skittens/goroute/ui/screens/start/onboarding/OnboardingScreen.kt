package ru.skittens.goroute.ui.screens.start.onboarding

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import ru.skittens.goroute.R
import ru.skittens.goroute.ui.elements.BodyText
import ru.skittens.goroute.ui.elements.ButtonText
import ru.skittens.goroute.ui.elements.PageIndicator
import ru.skittens.goroute.ui.navigation.Destinations
import ru.skittens.goroute.ui.navigation.NavigationFun
import ru.skittens.goroute.ui.screens.start.splash.HalfTransparentCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    navigateTo: NavigationFun,
    displayMetrics: DpSize = koinInject(),
    viewModel: OnboardingViewModel = koinInject()
) {
    val pagerState = rememberPagerState { viewModel.onboardings.size }
    val context = LocalContext.current

    val width = displayMetrics.width
    val height = displayMetrics.height
    val imageDrawable =
        remember { ContextCompat.getDrawable(context, R.drawable.backgroung_onboarding) }
    val bitmap = remember { drawableToBitmap(imageDrawable!!) }
    val imageBitmap = bitmap.asImageBitmap()
    val coroutineScope = rememberCoroutineScope()

    val offset by remember {
        derivedStateOf {
            val pageOffset = pagerState.currentPage + pagerState.currentPageOffsetFraction
            pageOffset
        }
    }

    val buttonShow by remember {
        derivedStateOf {
            pagerState.currentPage == 4
        }
    }

    Box(Modifier.fillMaxSize()) {
        Scaffold(
            Modifier.fillMaxSize().drawBehind {
                val translationFactor = 0.3f // 15% shift per each page
                val translationX = offset * size.width * translationFactor
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
            topBar = {
                OnBoardingTopBar(!buttonShow) {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.pageCount - 1)
                    }
                }
            }, containerColor = Color.White.copy(.5f)
        ) {
            Box(Modifier.fillMaxSize().padding(it)) {
                Column(Modifier.fillMaxSize()) {
                    HorizontalPager(pagerState, Modifier.weight(1f)) {
                        OnboardingItem(viewModel.onboardings[it])
                    }

                    Spacer(Modifier.height(34.dp))
                    AnimatedVisibility(buttonShow) {
                        FilledColorButton(
                            "Войти",
                            modifier = Modifier.padding(horizontal = 18.dp)
                        ) {
                            navigateTo(Destinations.Authentication)
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                    PageIndicator(
                        pagerState.pageCount,
                        Modifier.padding(bottom = 18.dp)
                            .align(Alignment.CenterHorizontally),
                        pagerState.currentPage,
                        MaterialTheme.colorScheme.onSurface,
                        Color(0xFF_1F_1F_1f).copy(.4f)
                    )
                }
            }
        }
    }
}

@Composable
fun FilledColorButton(
    text: String,
    colorButton: Color = MaterialTheme.colorScheme.secondaryContainer,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    FilledTonalButton(
        onClick,
        modifier.fillMaxWidth(),
        colors = ButtonDefaults.filledTonalButtonColors(colorButton),
        shape = RoundedCornerShape(24.dp)
    ) {
        ButtonText(
            text,
            Modifier.padding(vertical = 8.dp),
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

@Composable
private fun OnboardingItem(onboarding: Onboarding) {
    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            rememberAsyncImagePainter(onboarding.image),
            null,
            Modifier.fillMaxWidth(0.6f).aspectRatio(1f)
        )

        HalfTransparentCard(onboarding.title, onboarding.subTile)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnBoardingTopBar(isShowButton: Boolean, skipAction: () -> Unit) {
    TopAppBar(
        {},
        Modifier.fillMaxWidth().navigationBarsPadding(),
        { Image(painterResource(R.drawable.logo_small), null) },
        {
            AnimatedVisibility(isShowButton) {
                CustomTextButton("Пропустить", onClick = skipAction)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(Color.Transparent)
    )
}

@Composable
fun CustomTextButton(
    title: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface,
    onClick: () -> Unit
) {
    TextButton(onClick, modifier) {
        BodyText(title, color = color)
    }
}

fun drawableToBitmap(drawable: Drawable): Bitmap {
    when (drawable) {
        is BitmapDrawable -> {
            return drawable.bitmap
        }

        is VectorDrawable -> {
            val bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            return bitmap
        }

        else -> {
            throw IllegalArgumentException("Unsupported drawable type")
        }
    }
}