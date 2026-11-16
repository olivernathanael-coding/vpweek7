package com.oliver.vpweek_7.Soal2.navigation

import kotlinx.serialization.Serializable

@Serializable
object ArtistRoute

@Serializable
data class AlbumDetailRoute(val albumId: String)
