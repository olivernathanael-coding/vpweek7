package com.oliver.vpweek_7.Soal2.data.remote

import com.oliver.vpweek_7.Soal2.data.dto.AlbumResponse
import com.oliver.vpweek_7.Soal2.data.dto.ArtistResponse
import com.oliver.vpweek_7.Soal2.data.dto.TrackResponse
import retrofit2.http.GET
import retrofit2.http.Query
interface AudioAPIService {

    @GET("search.php")
    suspend fun searchArtist(@Query("s") artistName: String): ArtistResponse

    @GET("searchalbum.php")
    suspend fun searchAlbums(@Query("s") artistName: String): AlbumResponse

    @GET("album.php")
    suspend fun getAlbum(@Query("m") albumId: String): AlbumResponse

    @GET("track.php")
    suspend fun getTracks(@Query("m") albumId: String): TrackResponse
}
