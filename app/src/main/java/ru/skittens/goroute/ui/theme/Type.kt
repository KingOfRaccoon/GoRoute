package ru.skittens.goroute.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.skittens.goroute.R

val Typography = Typography(
    // BigTitle
    titleLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.jost)),
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.6.sp,
        letterSpacing = 0.sp
    ),

    // Title
    titleMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.jost)),
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.sp
    ),

    // Button
    bodyLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.jost)),
        fontWeight = FontWeight.Medium,
        fontSize = 17.sp,
        lineHeight = 22.1.sp,
        letterSpacing = 0.5.sp
    ),

    // Body
    bodyMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.jost)),
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 19.5.sp,
        letterSpacing = 0.sp
    ),

    // Caption
    labelMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.jost)),
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp,
        lineHeight = 16.9.sp,
        letterSpacing = 0.5.sp
    )
)