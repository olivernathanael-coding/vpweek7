package com.oliver.vpweek_7.Soal2.data.repository

import com.oliver.vpweek_7.Soal2.model.AlbumDetail
import com.oliver.vpweek_7.Soal2.model.ArtistPage
import com.oliver.vpweek_7.Soal2.data.remote.AudioAPIService
import com.oliver.vpweek_7.Soal2.util.NotFoundException
import com.oliver.vpweek_7.Soal2.util.safeCall
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class MusicRepository(
    private val api: AudioAPIService
) {

    suspend fun getArtistPage(name: String): Result<ArtistPage> = safeCall {
        coroutineScope {
            val artistRequest = async { api.searchArtist(name) }
            val albumsRequest = async { api.searchAlbums(name) }

            val artist = artistRequest.await().artists?.firstOrNull()?.toArtist()
                ?: throw NotFoundException("Artis \"$name\" tidak ditemukan")
            val albums = albumsRequest.await().album.orEmpty()
                .mapNotNull { it.toAlbum() }
                .sortedBy { it.year }

            ArtistPage(artist, albums)
        }
    }

    suspend fun getAlbumDetail(albumId: String): Result<AlbumDetail> = safeCall {
        coroutineScope {
            val albumRequest = async { api.getAlbum(albumId) }
            val tracksRequest = async { api.getTracks(albumId) }

            val album = albumRequest.await().album?.firstOrNull()?.toAlbum()
                ?: throw NotFoundException("Album tidak ditemukan")
            val tracks = tracksRequest.await().track.orEmpty()
                .mapNotNull { it.toTrack() }
                .sortedBy { it.number }

            AlbumDetail(album, tracks)
        }
    }
}
