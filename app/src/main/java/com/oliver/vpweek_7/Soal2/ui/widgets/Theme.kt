package com.oliver.vpweek_7.Soal2.ui.widgets

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Bg = Color(0xFF0B0D10)
val Surface1 = Color(0xFF161A20)
val Surface2 = Color(0xFF1F242C)
val Green = Color(0xFF1DB954)
val GreenDark = Color(0xFF0F3D22)
val TextPrimary = Color(0xFFFFFFFF)
val TextSecondary = Color(0xFF9AA3AE)
val ErrorRed = Color(0xFFFF5C5C)

private val Scheme = darkColorScheme(
    primary = Green,
    background = Bg,
    surface = Surface1,
    onPrimary = Color.Black,
    onBackground = TextPrimary,
    onSurface = TextPrimary
)

@Composable
fun MusicArtistTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = Scheme, content = content)
}
