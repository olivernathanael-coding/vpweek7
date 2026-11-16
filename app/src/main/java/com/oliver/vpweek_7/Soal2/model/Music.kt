package com.oliver.vpweek_7.Soal2.model

data class Artist(
    val id: String,
    val name: String,
    val genre: String,
    val country: String,
    val thumb: String?,
    val banner: String?,
    val biography: String
)

data class Album(
    val id: String,
    val name: String,
    val artist: String,
    val year: String,
    val genre: String,
    val cover: String?,
    val description: String
)

data class Track(
    val id: String,
    val number: Int,
    val title: String,
    val genre: String,
    val durationMs: Long
)

data class ArtistPage(
    val artist: Artist,
    val albums: List<Album>
)

data class AlbumDetail(
    val album: Album,
    val tracks: List<Track>
)
