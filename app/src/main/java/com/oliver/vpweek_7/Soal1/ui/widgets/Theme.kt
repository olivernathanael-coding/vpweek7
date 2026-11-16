package com.oliver.vpweek_7.Soal1.ui.widgets

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Navy = Color(0xFF0E131A)
val Panel = Color(0xFF18202B)
val PanelBorder = Color(0xFF263241)
val Red = Color(0xFFE53935)
val Mint = Color(0xFF1FD39B)
val MintDark = Color(0xFF0F3B33)
val Gold = Color(0xFFFFC53D)
val GoldDark = Color(0xFF3A3220)
val TextMain = Color(0xFFFFFFFF)
val TextMuted = Color(0xFF93A0B1)

private val Scheme = darkColorScheme(
    primary = Red,
    background = Navy,
    surface = Panel,
    onPrimary = Color.White,
    onBackground = TextMain,
    onSurface = TextMain
)

@Composable
fun PokeTrackerTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = Scheme, content = content)
}
