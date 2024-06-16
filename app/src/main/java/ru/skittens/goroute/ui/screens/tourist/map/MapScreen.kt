package ru.skittens.goroute.ui.screens.tourist.map

import android.graphics.Color.parseColor
import android.graphics.Typeface
import android.text.*
import android.text.style.*
import android.text.util.Linkify
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.core.text.toSpannable
import coil.compose.AsyncImage
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.Style
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.PolygonAnnotation
import com.mapbox.maps.extension.compose.annotation.generated.PolylineAnnotation
import com.mapbox.maps.extension.localization.localizeLabels
import com.mapbox.maps.plugin.annotation.generated.PolygonAnnotation
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotation
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import ru.kingofraccoons.domain.util.Resource
import ru.skittens.domain.entity.Area
import ru.skittens.domain.entity.Park
import ru.skittens.domain.entity.Route
import ru.skittens.goroute.R
import ru.skittens.goroute.ui.elements.BodyText
import ru.skittens.goroute.ui.elements.PageIndicator
import ru.skittens.goroute.ui.elements.TitleText
import ru.skittens.goroute.ui.navigation.Destinations
import ru.skittens.goroute.ui.navigation.NavigationFun
import java.util.*

@OptIn(MapboxExperimental::class, ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(navigateTo: NavigationFun, viewModel: MapViewModel = koinInject()) {
    val parks by viewModel.parksFlow.collectAsState()
    val areas by viewModel.areasFlow.collectAsState()
    val currentId by viewModel.currentIdFlow.collectAsState()
    var isShowArea by remember { mutableStateOf(false) }
    val routes by viewModel.routesFlow.collectAsState(Resource.Loading())
    val context = LocalContext.current
    val areaBottomSheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    var bottomSheetState by remember { mutableStateOf(BottomSheetState.ParkOrArea) }
    val state = rememberMapViewportState {
        setCameraOptions(CameraOptions.Builder().zoom(5.0).center(Point.fromLngLat(160.027, 57.124)).build())
    }

    BackHandler(bottomSheetState != BottomSheetState.ParkOrArea) {
        bottomSheetState.previousState?.let {
            bottomSheetState = it
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadParksAndAreas()
    }

    LaunchedEffect(Unit) {
        viewModel.loadRoutes()
    }

    if (isShowArea && currentId.isNotEmpty()) {
        areas.data?.find { currentId == it.id }?.let { area ->
            parks.data?.find { getParkIdOnAreaId(currentId) == it.id }?.let { park ->
                AreaBottomSheet(areaBottomSheetState, area, park, bottomSheetState, {
                    state.setCameraOptions {
                        center(
                            calculatePolygonCenter(
                                area.getGeometryData().areas.map { it.coordinates }.flatten()
                                    .map { it.map { Point.fromLngLat(it[0], it[1]) } })
                        ).build()
                    }
                }, { viewModel.setAreaId(it); navigateTo(Destinations.SelectRoute) }) {
                    isShowArea = false
                    coroutineScope.launch {
                        areaBottomSheetState.hide()
                    }
                }
            }
        }
    }


    Box(Modifier.fillMaxSize()) {
        MapboxMap(Modifier.fillMaxSize(), mapViewportState = state) {
            MapEffect(Unit) { mapView ->
                mapView.mapboxMap.loadStyle(Style.Companion.OUTDOORS) {
                    it.localizeLabels(Locale.getDefault())
                }
            }

            //        parks.data?.forEach { park ->
            //            ParkOrAreaItem(park) {
            //                println("set newId: " + park.id)
            //                viewModel.setAreaOrParkId(park.id)
            //
            //                Toast.makeText(context, "Меня зовут ${park.name}", Toast.LENGTH_SHORT).show()
            //                state.flyTo(CameraOptions.Builder().center(calculatePolygonCenter(it)).build())
            //                true
            //            }
            //        }

            routes.data?.forEach { route ->
                RouteMapItem(route) {
                    Toast.makeText(context, "У меня ${it.points.size}", Toast.LENGTH_SHORT).show()

                    true
                }
            }

            areas.data?.forEach { area ->
                ParkOrAreaItem(area) {
                    if (currentId != area.id) {
                        bottomSheetState = BottomSheetState.ParkOrArea
                        isShowArea = true
                        coroutineScope.launch {
                            areaBottomSheetState.show()
                        }
                        viewModel.setAreaOrParkId(area.id)

                        Toast.makeText(context, "Меня зовут ${area.name}", Toast.LENGTH_SHORT).show()
                        state.flyTo(CameraOptions.Builder().center(calculatePolygonCenter(it.points)).build())

                        true
                    } else {
                        false
                    }
                }
            }
        }

        FloatingActionButton(
            { navigateTo(Destinations.AddIncident) },
            Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ) {
            Icon(Icons.Default.Warning, null, tint = MaterialTheme.colorScheme.primary)
        }
    }
}

@OptIn(MapboxExperimental::class)
@Composable
fun ParkOrAreaItem(park: Park, onClick: (PolygonAnnotation) -> Boolean) {
    PolygonAnnotation(
        points = listOf(park.getPointsList().map { Point.fromLngLat(it[1], it[0]) }),
        fillColorInt = hexToColor(park.color),
        fillOutlineColorInt = hexToColor(park.borderColor),
        onClick = onClick
    )
}

@OptIn(MapboxExperimental::class)
@Composable
fun ParkOrAreaItem(area: Area, onClick: (PolygonAnnotation) -> Boolean) {
    area.getGeometryData().areas.forEach {
        println("ParkOrAreaItem")
        println(it.coordinates)
        PolygonAnnotation(
            points = it.coordinates.map { it.map { Point.fromLngLat(it[0], it[1]) } },
            fillColorInt = Color.Green.copy(.3f).toArgb(),
            fillOutlineColorInt = Color.Green.toArgb(),
            onClick = onClick
        )
    }
}

@OptIn(MapboxExperimental::class)
@Composable
fun RouteMapItem(route: Route, onClick: (PolylineAnnotation) -> Boolean) {
    route.getPathData().forEach {
        PolylineAnnotation(
            points = it.points.map { Point.fromLngLat(it[1], it[0]) },
            lineColorInt = hexToColor(it.color),
            lineOpacity = 30.0,
            lineWidth = 2.0,
            onClick = onClick
        )

        PolylineAnnotation(
            points = it.points.map { Point.fromLngLat(it[1], it[0]) },
            lineColorInt = Color.Transparent.toArgb(),
            lineWidth = 60.0,
            lineOpacity = 30.0,
            onClick = onClick
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AreaBottomSheet(
    sheetState: SheetState,
    area: Area,
    park: Park,
    state: BottomSheetState,
    centerOnArea: () -> Unit,
    openRoutes: (String) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismiss,
        Modifier.fillMaxWidth(),
        sheetState,
        tonalElevation = 0.dp,
        dragHandle = null
    ) {
//        AnimatedContent(state) {
//            when (it) {
//                BottomSheetState.ParkOrArea -> {
        val carouselPager = rememberPagerState { 2 }
        Column {
            Box {
                HorizontalPager(carouselPager) {
                    AsyncImage(
                        "https://green-button.empedokl.com/upload/park5.jpg",
                        null,
                        Modifier
                            .fillMaxWidth()
                            .aspectRatio(2f),
                        contentScale = ContentScale.Crop
                    )
                }

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    Arrangement.SpaceBetween,
                    Alignment.CenterVertically
                ) {

                    IconButton(
                        onDismiss,
                        Modifier,
                        colors = IconButtonDefaults.iconButtonColors(Color.White.copy(.4f))
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color.White)
                    }

                    TitleText(
                        area.subname.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.forLanguageTag(
                                    "ru"
                                )
                            ) else it.toString()
                        },
                        Modifier,
                        TextAlign.Center,
                        Color.White
                    )

                    IconButton(
                        {},
                        Modifier,
                        false,
                        colors = IconButtonDefaults.iconButtonColors(disabledContainerColor = Color.White)
                    ) {
                        Icon(Icons.Filled.BarChart, null, tint = MaterialTheme.colorScheme.primary)
                    }
                }

                PageIndicator(
                    carouselPager.pageCount,
                    Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 12.dp),
                    carouselPager.currentPage,
                    MaterialTheme.colorScheme.primary
                )
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp, 8.dp)
            ) {
                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                    TitleText(area.name)
                    TextButton({}) {
                        BodyText(
                            "На карте",
                            Modifier.align(Alignment.CenterVertically),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.width(4.dp))
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            null,
                            Modifier.align(Alignment.CenterVertically),
                            MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Row(
                    Modifier
                        .fillMaxWidth()
                        .aspectRatio(5f)
                ) {
                    Card(
                        { openRoutes(area.id) },
                        Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Column(
                            Modifier
                                .padding(15.dp, 9.dp)
                                .fillMaxSize(),
                            Arrangement.spacedBy(6.dp, Alignment.CenterVertically),
                            Alignment.CenterHorizontally
                        ) {
                            Icon(
                                painterResource(R.drawable.ic_routes),
                                null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            TitleText(
                                "Маршруты",
                                Modifier.fillMaxWidth(),
                                TextAlign.Center,
                                MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                Spacer(Modifier.height(18.dp))
                HorizontalDivider(
                    Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                )
                Spacer(Modifier.height(12.dp))

                val uriHandler = LocalUriHandler.current
                val body = SpannableString(Html.fromHtml(area.description))
                body.setSpan(RelativeSizeSpan(1.3f), 0, 7, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                val text = body.toAnnotateString(MaterialTheme.typography.bodyLarge, null, Color.Blue)
                val spannedText =
                    text.toSpannable() // превращаем в Spannable, так как Linkify работает со Spannable
                Linkify.addLinks(spannedText, Linkify.WEB_URLS) // Ищем и размечаем URLSpan

                ClickableText(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    onClick = { offset ->
                        text.getStringAnnotations(URL_TAG, offset, offset).firstOrNull()?.let {
                            uriHandler.openUri(it.item)
                        }
                    }
                )
            }
        }
    }

//                BottomSheetState.RoutesParkOrArea -> {
//                    Column(Modifier.fillMaxWidth()) {
//                        Box(
//                            Modifier
//                                .background(Color.Red)
//                                .fillMaxWidth()
//                                .aspectRatio(2f)
//                        )
//                    }
//                }
//            }
//        }
//    }
}

const val URL_TAG = "url"

fun Spanned.toAnnotateString(
    textStyle: TextStyle,
    baseSpanStyle: SpanStyle?,
    linkColor: Color
): AnnotatedString {
    return buildAnnotatedString {
        val spanned = this@toAnnotateString
        append(spanned.toString())
        baseSpanStyle?.let { addStyle(it, 0, length) }
        getSpans(0, spanned.length, Any::class.java).forEach { span ->
            val start = getSpanStart(span)
            val end = getSpanEnd(span)
            when (span) {
                is StyleSpan -> when (span.style) {
                    Typeface.BOLD -> addStyle(SpanStyle(fontWeight = FontWeight.Bold), start, end)
                    Typeface.ITALIC -> addStyle(SpanStyle(fontStyle = FontStyle.Italic), start, end)
                    Typeface.BOLD_ITALIC -> addStyle(
                        SpanStyle(fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic),
                        start,
                        end
                    )
                }

                is RelativeSizeSpan -> addStyle(SpanStyle(fontSize = span.sizeChange * textStyle.fontSize), start, end)
                is UnderlineSpan -> addStyle(SpanStyle(textDecoration = TextDecoration.Underline), start, end)
                is ForegroundColorSpan -> addStyle(SpanStyle(color = Color(span.foregroundColor)), start, end)
                is URLSpan -> {
                    addStyle(
                        SpanStyle(
                            textDecoration = TextDecoration.Underline,
                            color = linkColor
                        ), start, end
                    )
                    addStringAnnotation(URL_TAG, span.url, start, end)
                }
            }
        }
    }
}

fun hexToColor(hex: String): Int {
    // Убедитесь, что строка начинается с "0x"
    require(hex.startsWith("0x")) { "Hex color string should start with '0x'" }

    // Удалите "0x" в начале строки
    val hexColor = hex.removePrefix("0x")

    // Убедитесь, что длина строки составляет 8 символов (ARGB)
    require(hexColor.length == 8) { "Hex color string should be 8 characters long" }

    // Конвертируйте строку в цвет. Мы используем radix 16 для шестнадцатеричной системы.
    return parseColor("#$hexColor")
}

fun getParkIdOnAreaId(areaId: String) = when (areaId) {
    "63c7d0a6ddfd2350eaac22cd" -> "63c7d7554c0f32b465830e98"
    "63900efa400559793e2ddc82" -> "6398f8cd07256326ba3f9371"
    "638f2662400559793e2ddc81" -> "6398f91807256326ba3f9372"
    "638f25e2400559793e2ddc80" -> "6398f95a07256326ba3f9373"
    "63901d17400559793e2ddc83" -> "6398f8cd07256326ba3f9371"
    else -> "6398f8cd07256326ba3f9371"
}

fun calculatePolygonCenter(polygon: List<List<Point>>): Point {
    val coordinates = polygon.flatten() // Используем кольцо внешних координат
    var totalLat = 0.0
    var totalLng = 0.0
    val count = coordinates.size

    for (coordinate in coordinates) {
        totalLat += coordinate.latitude()
        totalLng += coordinate.longitude()
    }

    val avgLat = totalLat / count
    val avgLng = totalLng / count

    return Point.fromLngLat(avgLng, avgLat)
}