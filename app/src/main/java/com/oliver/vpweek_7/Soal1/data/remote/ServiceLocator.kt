package com.oliver.vpweek_7.Soal1.data.remote

import com.oliver.vpweek_7.Soal1.data.repository.CardRepository
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object ServiceLocator {
// API Cadangan: pokeprice_free_95478b80d5e02e6beceab1058e513123c5d19ebda5395e24
    private const val API_KEY = "pokeprice_free_cb9239661916b8eea0f3a7d7c79e3043336296a1f43369e1"

    private const val BASE_URL = "https://www.pokemonpricetracker.com/api/v2/"

    private val json = Json { ignoreUnknownKeys = true }

    private val authInterceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $API_KEY")
            .build()
        chain.proceed(request)
    }

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
        redactHeader("Authorization")
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(logging)
        .build()

    val api: PokemonApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(PokemonApiService::class.java)

    val repository: CardRepository by lazy { CardRepository(api) }
}
