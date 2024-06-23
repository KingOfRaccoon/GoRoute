package ru.skittens.goroute.ui.screens.tourist.map

import android.graphics.Bitmap
import android.graphics.Color.parseColor
import android.graphics.Typeface
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.text.style.URLSpan
import android.text.style.UnderlineSpan
import android.text.util.Linkify
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.PhoneInTalk
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.EmojiPeople
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.core.content.ContextCompat
import androidx.core.text.toSpannable
import coil.compose.AsyncImage
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.Style
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotation
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
import ru.skittens.goroute.ui.elements.ButtonText
import ru.skittens.goroute.ui.elements.PageIndicator
import ru.skittens.goroute.ui.elements.TitleText
import ru.skittens.goroute.ui.navigation.Destinations
import ru.skittens.goroute.ui.navigation.NavigationFun
import ru.skittens.goroute.ui.screens.start.onboarding.FilledColorButton
import ru.skittens.goroute.ui.screens.start.onboarding.drawableToBitmap
import ru.skittens.goroute.ui.screens.tourist.DefaultTopBar
import java.util.Locale

@OptIn(MapboxExperimental::class, ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(navigateTo: NavigationFun, viewModel: MapViewModel = koinInject()) {
    val parks by viewModel.parksFlow.collectAsState()
    val filteredAreas by viewModel.filteredAreasFlow.collectAsState(Resource.Loading())
    val areas by viewModel.areasFlow.collectAsState()
    val filters by viewModel.filtersFlow.collectAsState()
    val currentId by viewModel.currentIdFlow.collectAsState()
    var isShowArea by remember { mutableStateOf(false) }
    var isShowFilter by remember { mutableStateOf(false) }
    val routes by viewModel.routesFlow.collectAsState(Resource.Loading())
    val context = LocalContext.current
    val areaBottomSheetState = rememberModalBottomSheetState()
    val filterBottomSheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    val state = rememberMapViewportState {
        setCameraOptions(
            CameraOptions.Builder().zoom(5.0).center(Point.fromLngLat(160.027, 57.124)).build()
        )
    }
    val imageDrawable = remember { ContextCompat.getDrawable(context, R.drawable.ic_pin) }
    val bitmap = remember { drawableToBitmap(imageDrawable!!) }

    LaunchedEffect(Unit) {
        viewModel.loadParksAndAreas()
    }

    LaunchedEffect(Unit) {
        viewModel.loadRoutes()
    }

    if (isShowFilter && areas.data != null) FilterBottomSheet(
        filterBottomSheetState,
        areas.data.orEmpty(),
        filters,
        viewModel::addFilter,
        viewModel::addFilters
    ) {
        isShowFilter = false
        coroutineScope.launch {
            filterBottomSheetState.hide()
        }
    }

    if (isShowArea && currentId.isNotEmpty()) {
        areas.data?.find { currentId == it.id }?.let { area ->
            parks.data?.find { getParkIdOnAreaId(currentId) == it.id }?.let { park ->
                AreaBottomSheet(areaBottomSheetState, area, park, {
                    state.setCameraOptions {
                        center(calculatePolygonCenter(area.getGeometryData().areas.map { it.coordinates }
                            .flatten().map { it.map { Point.fromLngLat(it[0], it[1]) } })
                        ).build()
                    }
                }, { id, name ->
                    viewModel.setAreaId(id)
                    viewModel.topBarValue = name
                    navigateTo(Destinations.SelectRoute)
                }, navigateTo) {
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
//                    Toast.makeText(context, "У меня ${it.points.size}", Toast.LENGTH_SHORT).show()

                    true
                }
            }

            filteredAreas.data?.forEach { area ->
                ParkOrAreaItem(area, bitmap) {
                    isShowArea = true
                    coroutineScope.launch {
                        areaBottomSheetState.show()
                    }
                    if (currentId != area.id) {
                        viewModel.setAreaOrParkId(area.id)

                        Toast.makeText(context, "Меня зовут ${area.name}", Toast.LENGTH_SHORT)
                            .show()
                        state.flyTo(
                            CameraOptions.Builder().zoom(7.0)
                                .center(calculatePolygonCenter(it.points)).build()
                        )

                        true
                    } else {
                        false
                    }
                }
            }
        }

        DefaultTopBar()

        IconButton(
            onClick = {
                isShowFilter = true
                coroutineScope.launch {
                    filterBottomSheetState.show()
                }
            },
            Modifier
                .padding(horizontal = 18.dp, vertical = 5.dp)
                .align(Alignment.TopEnd),
            colors = IconButtonDefaults.filledIconButtonColors(Color(0xBFFFFFFF))
        ) {
            Icon(Icons.Default.Layers, null)
        }

        Column(
            Modifier
                .align(Alignment.CenterEnd)
                .padding(18.dp), Arrangement.spacedBy(10.dp)
        ) {
            IconButton(
                { state.setCameraOptions { zoom((state.cameraState?.zoom ?: 1.0) + 1.0) } },
                colors = IconButtonDefaults.iconButtonColors(Color(0xD9FFFFFF)),
                modifier = Modifier.shadow(
                        elevation = 16.dp,
                        spotColor = Color(0x1F000000),
                        ambientColor = Color(0x1F000000)
                    )
            ) {
                Icon(Icons.Default.Add, null, tint = Color.Black)
            }
            IconButton(
                { state.setCameraOptions { zoom((state.cameraState?.zoom ?: 1.0) - 1.0) } },
                colors = IconButtonDefaults.iconButtonColors(Color(0xD9FFFFFF)),
                modifier = Modifier.shadow(
                        elevation = 16.dp,
                        spotColor = Color(0x1F000000),
                        ambientColor = Color(0x1F000000)
                    )
            ) {
                Icon(Icons.Default.Remove, null, tint = Color.Black)
            }
        }
        ExtendedFloatingActionButton(
            onClick = { navigateTo(Destinations.AddIncident) },
            icon = { Icon(Icons.Outlined.EmojiPeople, null, tint = Color.Black) },
            text = { BodyText(text = "Сообщить о проблеме", color = Color.Black) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            contentColor = Color(0xB2FFFFFF),
            shape = RoundedCornerShape(size = 24.dp),
            containerColor = Color(0xD9FFFFFF),
        )
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
fun ParkOrAreaItem(area: Area, iconPin: Bitmap, onClick: (PolygonAnnotation) -> Boolean) {
    area.getGeometryData().areas.forEach {
        println("ParkOrAreaItem")
        println(it.coordinates)
        PolygonAnnotation(
            points = it.coordinates.map { it.map { Point.fromLngLat(it[0], it[1]) } },
            fillColorInt = Color.Green.copy(.3f).toArgb(),
            fillOutlineColorInt = Color.Green.toArgb(),
            onClick = onClick
        )

        PointAnnotation(calculatePolygonCenter(it.coordinates.map {
            it.map { Point.fromLngLat(it[0], it[1]) }
        }), iconPin)
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
            lineWidth = 3.0,
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
fun FilterBottomSheet(
    sheetState: SheetState,
    areas: List<Area>,
    filters: List<String>,
    addFilter: (String) -> Unit,
    addFilters: (List<String>) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val items = remember { listOf("Туризм", "Экология") }
    ModalBottomSheet(
        onDismiss,
        Modifier.fillMaxWidth(),
        sheetState,
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 0.dp,
    ) {
        LazyColumn(
            Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            stickyHeader {
                Column(
                    Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TitleText("Слои на карте")
                    Box(Modifier.fillMaxWidth()) {
                        TextSwitch(
                            Modifier.align(Alignment.TopCenter), selectedIndex, items
                        ) { selectedIndex = it }
                    }
                }
            }

            item {
                ExpandCard({
                    ButtonText("Природные парки")
                    Spacer(Modifier.weight(1f))
                    AnimatedContent(targetState = areas.all { it.id in filters }) {
                        Icon(if (it) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            null,
                            Modifier
                                .clip(CircleShape)
                                .clickable { addFilters(areas.map { it.id }) })
                    }
                }) {
                    Column {
                        areas.forEach { area ->
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(start = 32.dp, end = 8.dp),
                                Arrangement.SpaceBetween,
                                Alignment.CenterVertically
                            ) {
                                AnimatedContent(targetState = area.id in filters) {
                                    ButtonText(
                                        area.name,
                                        color = if (it) Color.Gray else MaterialTheme.colorScheme.onBackground
                                    )
                                }

                                AnimatedContent(targetState = area.id in filters) {
                                    IconButton(
                                        onClick = { addFilter(area.id) },
                                        colors = IconButtonDefaults.iconButtonColors(Color.Transparent)
                                    ) {
                                        Icon(
                                            if (it) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                            null,
                                            tint = if (it) Color.Gray else MaterialTheme.colorScheme.onBackground
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun ContentDrawScope.drawWithLayer(block: ContentDrawScope.() -> Unit) {
    with(drawContext.canvas.nativeCanvas) {
        val checkPoint = saveLayer(null, null)
        block()
        restoreToCount(checkPoint)
    }
}

@Composable
fun TextSwitch(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    items: List<String>,
    backgroundColor: Color = MaterialTheme.colorScheme.onSecondary,
    accentColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    onSelectionChange: (Int) -> Unit,
) {

    BoxWithConstraints(
        modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(24.dp))
            .height(56.dp)
            .background(backgroundColor)
            .padding(8.dp)
    ) {
        if (items.isNotEmpty()) {
            val maxWidth = this.maxWidth
            val tabWidth = maxWidth / items.size

            val indicatorOffset by animateDpAsState(
                targetValue = tabWidth * selectedIndex,
                animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing),
                label = "indicator offset"
            )

            Row(modifier = Modifier
                .fillMaxWidth()
                .drawWithContent {
                    // This is for setting black tex while drawing on white background
                    val padding = 6.dp.toPx()
                    drawRoundRect(
                        topLeft = Offset(x = indicatorOffset.toPx() + padding, padding),
                        size = Size(size.width / 2 - padding * 2, size.height - padding * 2),
                        color = Color.White,
                        cornerRadius = CornerRadius(18.dp.toPx(), 18.dp.toPx()),
                    )

                    drawWithLayer {
                        drawContent()

                        // This is white top rounded rectangle
                        drawRoundRect(
                            topLeft = Offset(x = indicatorOffset.toPx(), 0f),
                            size = Size(size.width / 2, size.height),
                            color = accentColor,
                            cornerRadius = CornerRadius(size.width / 2, size.width / 2),
                            blendMode = BlendMode.SrcOut
                        )
                    }
                }) {
                items.forEachIndexed { index, text ->
                    Box(modifier = Modifier
                        .width(tabWidth)
                        .fillMaxHeight()
                        .clickable(interactionSource = remember {
                            MutableInteractionSource()
                        }, indication = null, onClick = {
                            onSelectionChange(index)
                        }), contentAlignment = Alignment.Center) {
                        ButtonText(text)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AreaBottomSheet(
    sheetState: SheetState,
    area: Area,
    park: Park,
    centerOnArea: () -> Unit,
    openRoutes: (String, String) -> Unit,
    navigateTo: NavigationFun,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismiss,
        Modifier.fillMaxWidth(),
        sheetState,
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 0.dp,
        dragHandle = null
    ) {
        val carouselPager = rememberPagerState { 2 }
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.background(Color.White)) {
            stickyHeader {
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
                            }, Modifier, TextAlign.Center, Color.White
                        )

                        IconButton(
                            {},
                            Modifier,
                            false,
                            colors = IconButtonDefaults.iconButtonColors(disabledContainerColor = Color.White)
                        ) {
                            Icon(
                                Icons.Filled.BarChart,
                                null,
                                tint = MaterialTheme.colorScheme.primary
                            )
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
            }

            item {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp),
                    Arrangement.SpaceBetween,
                    Alignment.CenterVertically
                ) {
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
            }

            item {
                FilledColorButton(
                    text = "Получить разрешение",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp, 0.dp)
                ) {
                    openRoutes(area.id, area.name)
                }
            }

            item {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp)
                        .aspectRatio(5f),
                    Arrangement.spacedBy(12.dp)
                ) {
                    AreaBottomSheetButton("Маршруты", R.drawable.ic_routes) {
                        openRoutes(area.id, area.name)
                    }

                    AreaBottomSheetButton("Экология", Icons.Default.Eco) {

                    }
                }
            }

            item {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .aspectRatio(5f)
                        .padding(16.dp, 0.dp)
                ) {
                    AreaBottomSheetButton("Достопримичастельности", Icons.Default.PhotoCamera) {

                    }
                }
            }

            item {
                HorizontalDivider(
                    Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .padding(16.dp, 0.dp)
                )
            }

            item {
                val uriHandler = LocalUriHandler.current
                val body = SpannableString(Html.fromHtml(area.description.trimEnd('\n')))
                body.setSpan(RelativeSizeSpan(1.3f), 0, 7, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                val text =
                    body.toAnnotateString(MaterialTheme.typography.bodyLarge, null, Color.Blue)
                val spannedText =
                    text.toSpannable() // превращаем в Spannable, так как Linkify работает со Spannable
                Linkify.addLinks(spannedText, Linkify.WEB_URLS) // Ищем и размечаем URLSpan

                ClickableText(modifier = Modifier.padding(16.dp, 0.dp),
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    onClick = { offset ->
                        text.getStringAnnotations(URL_TAG, offset, offset).firstOrNull()?.let {
                            uriHandler.openUri(it.item)
                        }
                    })
            }

            item {
                ExpandCard({ TitleText("Контактная информация") }) {
                    Column(Modifier.fillMaxWidth().padding(top = 12.dp, bottom = 12.dp), Arrangement.spacedBy(12.dp)) {
                        InfoItem(
                            icon = Icons.Default.PhoneInTalk,
                            title = "Телефон:",
                            values = listOf("+7 (943)-879-98-34", "+7 (943)-879-98-36")
                        )
                        InfoItem(
                            icon = Icons.Default.Event,
                            title = "График работы:",
                            values = listOf("ПН-ПТ с 8:00 до 19:00")
                        )
                        InfoItem(icon = Icons.Default.Language,
                            annotatedString = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        textDecoration = TextDecoration.Underline
                                    )
                                ) {
                                    append("Официальный сайт")
                                }
                                addStringAnnotation(
                                    tag = "URL",
                                    annotation = "https://text.ru/seo",
                                    start = 0,
                                    end = 15
                                )
                            })
                    }
                }
            }

            item {
                FilledColorButton(
                    text = "Пойти в поход", modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp, 0.dp)
                ) {
                    openRoutes(area.id, area.name)
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InfoItem(icon: ImageVector, title: String, values: List<String>) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = 18.dp), Arrangement.spacedBy(8.dp)
    ) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            ButtonText(title)
            FlowRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                values.forEach {
                    ButtonText(text = it)
                }
            }
        }
    }
}

@Composable
fun InfoItem(icon: ImageVector, annotatedString: AnnotatedString) {
    val uriHandler = LocalUriHandler.current
    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = 18.dp),
        Arrangement.spacedBy(8.dp),
        Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        ClickableText(text = annotatedString,
            style = MaterialTheme.typography.bodyLarge,
            onClick = { offset ->
                annotatedString.getStringAnnotations(URL_TAG, start = offset, end = offset)
                    .firstOrNull()?.let {
                        uriHandler.openUri(it.item)
                    }
            })
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            Icons.Default.ArrowOutward,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun RowScope.AreaBottomSheetButton(title: String, @DrawableRes icon: Int, onClick: () -> Unit) {
    Card(
        onClick,
        Modifier.Companion
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
                painterResource(icon), null, tint = MaterialTheme.colorScheme.primary
            )
            TitleText(
                title, Modifier.fillMaxWidth(), TextAlign.Center, MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun RowScope.AreaBottomSheetButton(title: String, icon: ImageVector, onClick: () -> Unit) {
    Card(
        onClick,
        Modifier.Companion
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
                icon, null, tint = MaterialTheme.colorScheme.primary
            )
            TitleText(
                title, Modifier.fillMaxWidth(), TextAlign.Center, MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun ExpandCard(
    content: @Composable RowScope.(Boolean) -> Unit, expandContent: @Composable (Boolean) -> Unit
) {
    var isExpand by remember { mutableStateOf(false) }
    val rotationAngle by animateFloatAsState(if (isExpand) 180f else 0f)

    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp)
    ) {
        Card(
            { isExpand = !isExpand },
            Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 24.dp,
                    spotColor = Color(0x0D000000),
                    ambientColor = Color(0x0D000000)
                ),
            shape = RoundedCornerShape(24.dp),
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.KeyboardArrowDown,
                    null,
                    Modifier.graphicsLayer(rotationX = rotationAngle)
                )
                Spacer(Modifier.width(8.dp))
                content(isExpand)
            }
        }
        AnimatedVisibility(visible = isExpand) {
            Spacer(modifier = Modifier.height(12.dp))
            expandContent(isExpand)
        }
    }
}

const val URL_TAG = "url"

fun Spanned.toAnnotateString(
    textStyle: TextStyle, baseSpanStyle: SpanStyle?, linkColor: Color
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

                is RelativeSizeSpan -> addStyle(
                    SpanStyle(fontSize = span.sizeChange * textStyle.fontSize), start, end
                )

                is UnderlineSpan -> addStyle(
                    SpanStyle(textDecoration = TextDecoration.Underline), start, end
                )

                is ForegroundColorSpan -> addStyle(
                    SpanStyle(color = Color(span.foregroundColor)), start, end
                )

                is URLSpan -> {
                    addStyle(
                        SpanStyle(
                            textDecoration = TextDecoration.Underline, color = linkColor
                        ), start, end
                    )
                    addStringAnnotation(URL_TAG, span.url, start, end)
                }
            }
        }
    }
}

fun hexToColor(hex: String): Int {
    require(hex.startsWith("0x")) { "Hex color string should start with '0x'" }
    val hexColor = hex.removePrefix("0x")
    require(hexColor.length == 8) { "Hex color string should be 8 characters long" }
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