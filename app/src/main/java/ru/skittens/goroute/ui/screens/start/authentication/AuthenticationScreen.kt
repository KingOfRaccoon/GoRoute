package ru.skittens.goroute.ui.screens.start.authentication

import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import org.koin.compose.koinInject
import ru.kingofraccoons.domain.util.Resource
import ru.skittens.goroute.R
import ru.skittens.goroute.ui.elements.BigTitleText
import ru.skittens.goroute.ui.elements.ButtonText
import ru.skittens.goroute.ui.navigation.Destinations
import ru.skittens.goroute.ui.navigation.NavigationFun
import ru.skittens.goroute.ui.screens.start.AuthenticationViewModel
import ru.skittens.goroute.ui.screens.start.onboarding.FilledColorButton
import ru.skittens.goroute.ui.screens.start.onboarding.drawableToBitmap

@Composable
fun AuthenticationScreen(
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

    Scaffold(Modifier.fillMaxSize().drawBehind {
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
            Modifier.fillMaxSize().padding(it).padding(horizontal = 18.dp),
            Arrangement.spacedBy(18.dp),
            Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(12.dp))
            Image(
                painterResource(R.drawable.logo_big),
                null,
                Modifier.fillMaxWidth(0.3f).aspectRatio(1f)
            )
            Spacer(Modifier.height(90.dp))

            LoginTextField(viewModel.login, viewModel::login::set)
            PasswordTextField(viewModel.password, viewModel::password::set)
//            CustomTextButton("Забыли пароль?", Modifier.align(Alignment.End)) { }
            Spacer(Modifier.weight(1f))
            FilledColorButton("Войти", onClick = viewModel::authenticationUser)
            FilledColorButton("Создать аккаунт", MaterialTheme.colorScheme.onSurface.copy(.8f)) {
                navigateTo(Destinations.Registration)
            }
            Spacer(Modifier.height(20.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTitle(title: String) {
    TopAppBar(
        { BigTitleText(title) },
        colors = TopAppBarDefaults.topAppBarColors(Color.Transparent)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(password: String, updatePassword: (String) -> Unit) {
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    TextField(
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(24.dp),
        value = password,
        onValueChange = updatePassword,
        singleLine = true,
        placeholder = {
            ButtonText(
                "Пароль",
                color = MaterialTheme.colorScheme.onBackground.copy(.4f)
            )
        },
        visualTransformation =
        if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(onClick = { passwordHidden = !passwordHidden }) {
                val visibilityIcon =
                    if (passwordHidden) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                // Please provide localized description for accessibility services
                val description = if (passwordHidden) "Show password" else "Hide password"
                Icon(
                    imageVector = visibilityIcon,
                    contentDescription = description,
                    tint = MaterialTheme.colorScheme.onBackground.copy(.4f)
                )
            }
        }
    )
}

@Composable
fun LoginTextField(login: String, updateLogin: (String) -> Unit) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(24.dp),
        value = login,
        onValueChange = updateLogin,
        singleLine = true,
        placeholder = {
            ButtonText(
                "Почта",
                color = MaterialTheme.colorScheme.onBackground.copy(.4f)
            )
        },
        visualTransformation = VisualTransformation.None,
    )
}

fun getScreenSize(context: Context): DpSize {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowManager = context.getSystemService(WindowManager::class.java)
        val metrics = windowManager.maximumWindowMetrics
        val width = metrics.bounds.width()
        val height = metrics.bounds.height()
        DpSize(width.dp, height.dp)
    } else {
        val displayMetrics = DisplayMetrics()
        @Suppress("DEPRECATION")
        (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        val height = displayMetrics.heightPixels
        DpSize(width.dp, height.dp)
    }
}