package com.oliver.vpweek_7.Soal2.data.repository

import com.oliver.vpweek_7.Soal2.data.dto.AlbumDto
import com.oliver.vpweek_7.Soal2.data.dto.ArtistDto
import com.oliver.vpweek_7.Soal2.data.dto.TrackDto
import com.oliver.vpweek_7.Soal2.model.Album
import com.oliver.vpweek_7.Soal2.model.Artist
import com.oliver.vpweek_7.Soal2.model.Track

fun ArtistDto.toArtist(): Artist? {
    val id = id ?: return null
    return Artist(
        id = id,
        name = name.orEmpty(),
        genre = genre.orEmpty(),
        country = country.orEmpty(),
        thumb = thumb,
        banner = fanart ?: banner ?: thumb,
        biography = (biography ?: biographyEn).orEmpty()
    )
}

fun AlbumDto.toAlbum(): Album? {
    val id = id ?: return null
    return Album(
        id = id,
        name = name.orEmpty(),
        artist = artist.orEmpty(),
        year = year.orEmpty(),
        genre = genre.orEmpty(),
        cover = cover,
        description = (description ?: descriptionEn).orEmpty()
    )
}

fun TrackDto.toTrack(): Track? {
    val id = id ?: return null
    return Track(
        id = id,
        number = number?.toIntOrNull() ?: 0,
        title = title.orEmpty(),
        genre = genre.orEmpty(),
        durationMs = duration?.toLongOrNull() ?: 0L
    )
}
