package com.oliver.vpweek_7.Soal1.data.remote

import com.oliver.vpweek_7.Soal1.data.dto.CardsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonApiService {

    @GET("cards")
    suspend fun searchCards(
        @Query("search") search: String,
        @Query("limit") limit: Int
    ): CardsResponse
}
