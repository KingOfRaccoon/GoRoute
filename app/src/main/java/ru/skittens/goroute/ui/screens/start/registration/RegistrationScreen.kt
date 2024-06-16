package ru.skittens.goroute.ui.screens.start.registration

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
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
import org.koin.compose.koinInject
import ru.kingofraccoons.domain.util.Resource
import ru.skittens.goroute.R
import ru.skittens.goroute.ui.navigation.Destinations
import ru.skittens.goroute.ui.navigation.NavigationFun
import ru.skittens.goroute.ui.screens.start.AuthenticationViewModel
import ru.skittens.goroute.ui.screens.start.authentication.LoginTextField
import ru.skittens.goroute.ui.screens.start.authentication.MainTitle
import ru.skittens.goroute.ui.screens.start.authentication.NameTextField
import ru.skittens.goroute.ui.screens.start.authentication.PasswordTextField
import ru.skittens.goroute.ui.screens.start.onboarding.FilledColorButton
import ru.skittens.goroute.ui.screens.start.onboarding.drawableToBitmap

@Composable
fun RegistrationScreen(
    navigateTo: NavigationFun,
    displayMetrics: DpSize = koinInject(),
    viewModel: AuthenticationViewModel = koinInject()
) {
    val user by viewModel.user.collectAsState()
    val context = LocalContext.current

    val width = displayMetrics.width
    val height = displayMetrics.height

    val imageDrawable =
        remember { ContextCompat.getDrawable(context, R.drawable.backgroung_onboarding) }
    val bitmap = remember { drawableToBitmap(imageDrawable!!) }
    val imageBitmap = bitmap.asImageBitmap()

    LaunchedEffect(user) {
        if (user is Resource.Success)
            when (user.data?.role) {
                "USER" -> navigateTo(Destinations.MainTourist)
                "MODERATOR" -> navigateTo(Destinations.MainEmployee)
                "ADMIN" -> navigateTo(Destinations.MainAdmin)
                else -> navigateTo(Destinations.MainTourist)
            }
    }

    Scaffold(
        Modifier
            .fillMaxSize()
            .drawBehind {
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
        containerColor = Color.White.copy(.5f), topBar = { MainTitle("") }) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 18.dp),
            Arrangement.spacedBy(18.dp),
            Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(12.dp))
            Image(
                painterResource(R.drawable.logo_big),
                null,
                Modifier.fillMaxWidth(1f)
            )
            Spacer(Modifier.weight(.5f))

            NameTextField(viewModel.fullName, viewModel::fullName::set)
            LoginTextField(viewModel.login, viewModel::login::set)
            PasswordTextField(viewModel.password, viewModel::password::set)

            Spacer(Modifier.weight(1f))
            FilledColorButton("Создать аккаунт", onClick = viewModel::registrationUser)
//            Spacer(Modifier.height(12.dp))
            FilledColorButton("У меня есть аккаунт", MaterialTheme.colorScheme.onSurface.copy(.8f)) {
                navigateTo(Destinations.Authentication)
            }
            Spacer(Modifier.height(20.dp))
        }
    }
}