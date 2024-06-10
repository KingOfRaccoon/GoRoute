package ru.skittens.goroute.ui.elements

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun AutoSizedText(
    modifier: Modifier = Modifier,
    text: String = "",
    annotatedText: AnnotatedString? = null,
    style: TextStyle = LocalTextStyle.current,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
    color: Color = Color.Unspecified,
    minFontSize: TextUnit = 12.sp,
) {
    var scaledTextStyle by remember { mutableStateOf(style) }
    var readyToDraw by remember { mutableStateOf(false) }

    if (annotatedText != null) {
        Text(
            modifier = modifier
                .drawWithContent {
                    if (readyToDraw) {
                        drawContent()
                    }
                },
            color = color,
            text = annotatedText,
            textAlign = textAlign,
            style = scaledTextStyle,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { textLayoutResult ->
                if (textLayoutResult.hasVisualOverflow && scaledTextStyle.fontSize > minFontSize) {
                    scaledTextStyle =
                        scaledTextStyle.copy(fontSize = scaledTextStyle.fontSize * 0.9)
                } else {
                    readyToDraw = true
                }
            },
            maxLines = maxLines
        )
    } else {
        Text(
            modifier = modifier
                .drawWithContent {
                    if (readyToDraw) {
                        drawContent()
                    }
                },
            color = color,
            text = text,
            style = scaledTextStyle,
            textAlign = textAlign,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { textLayoutResult ->
                if (textLayoutResult.hasVisualOverflow && scaledTextStyle.fontSize > minFontSize) {
                    scaledTextStyle =
                        scaledTextStyle.copy(fontSize = (scaledTextStyle.fontSize * 0.9))
                } else {
                    readyToDraw = true
                }
            },
            maxLines = maxLines
        )
    }
}

@Composable
fun BigTitleText(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = MaterialTheme.colorScheme.onBackground,
) {
    AutoSizedText(
        modifier,
        text,
        style = MaterialTheme.typography.titleLarge,
        textAlign = textAlign,
        color = color,
    )
}

@Composable
fun TitleText(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = MaterialTheme.colorScheme.onBackground
) {
    AutoSizedText(
        modifier,
        text,
        style = MaterialTheme.typography.titleMedium,
        textAlign = textAlign,
        color = color
    )
}

@Composable
fun ButtonText(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = MaterialTheme.colorScheme.onBackground
) {
    AutoSizedText(
        modifier, text, style = MaterialTheme.typography.bodyLarge, textAlign = textAlign,
        color = color
    )
}

@Composable
fun BodyText(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = MaterialTheme.colorScheme.onBackground
) {
    AutoSizedText(
        modifier,
        text,
        style = MaterialTheme.typography.bodyMedium,
        textAlign = textAlign,
        color = color
    )
}

@Composable
fun CaptionText(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = MaterialTheme.colorScheme.onBackground
) {
    AutoSizedText(
        modifier,
        text,
        style = MaterialTheme.typography.labelMedium,
        textAlign = textAlign,
        color = color
    )
}