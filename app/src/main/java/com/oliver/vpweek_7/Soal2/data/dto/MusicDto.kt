package com.oliver.vpweek_7.Soal2.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtistResponse(
    val artists: List<ArtistDto>? = null
)

@Serializable
data class ArtistDto(
    @SerialName("idArtist") val id: String? = null,
    @SerialName("strArtist") val name: String? = null,
    @SerialName("strGenre") val genre: String? = null,
    @SerialName("strCountry") val country: String? = null,
    @SerialName("strArtistThumb") val thumb: String? = null,
    @SerialName("strArtistBanner") val banner: String? = null,
    @SerialName("strArtistFanart") val fanart: String? = null,
    @SerialName("strBiography") val biography: String? = null,
    @SerialName("strBiographyEN") val biographyEn: String? = null
)

@Serializable
data class AlbumResponse(
    val album: List<AlbumDto>? = null
)

@Serializable
data class AlbumDto(
    @SerialName("idAlbum") val id: String? = null,
    @SerialName("strAlbum") val name: String? = null,
    @SerialName("strArtist") val artist: String? = null,
    @SerialName("intYearReleased") val year: String? = null,
    @SerialName("strGenre") val genre: String? = null,
    @SerialName("strAlbumThumb") val cover: String? = null,
    @SerialName("strDescription") val description: String? = null,
    @SerialName("strDescriptionEN") val descriptionEn: String? = null
)

@Serializable
data class TrackResponse(
    val track: List<TrackDto>? = null
)

@Serializable
data class TrackDto(
    @SerialName("idTrack") val id: String? = null,
    @SerialName("intTrackNumber") val number: String? = null,
    @SerialName("strTrack") val title: String? = null,
    @SerialName("strGenre") val genre: String? = null,
    @SerialName("intDuration") val duration: String? = null
)
