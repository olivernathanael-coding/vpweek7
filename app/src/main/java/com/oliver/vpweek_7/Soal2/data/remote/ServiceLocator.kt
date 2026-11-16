package com.oliver.vpweek_7.Soal2.data.remote

import com.oliver.vpweek_7.Soal2.data.repository.MusicRepository
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
object ServiceLocator {

    private const val API_KEY = "123"

    private const val BASE_URL = "https://www.theaudiodb.com/api/v1/json/$API_KEY/"

    private val json = Json { ignoreUnknownKeys = true }

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    val api: AudioAPIService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(AudioAPIService::class.java)

    val repository: MusicRepository by lazy { MusicRepository(api) }
}
