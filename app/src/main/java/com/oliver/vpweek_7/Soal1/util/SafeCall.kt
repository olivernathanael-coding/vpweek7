package com.oliver.vpweek_7.Soal1.util

import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import java.io.IOException
import java.util.Locale

class NotFoundException(message: String) : Exception(message)

suspend fun <T> safeCall(block: suspend () -> T): Result<T> =
    try {
        Result.success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(e)
    }

fun Throwable.toMessage(): String = when (this) {
    is NotFoundException -> message ?: "Kartu tidak ditemukan"
    is IOException -> "Koneksi gagal. Periksa koneksi internet kamu."
    is HttpException -> when (code()) {
        401 -> "API key tidak valid. Periksa API_KEY di ServiceLocator.kt."
        429 -> "Kuota API harian habis (jatah gratis 100 kredit/hari). Coba lagi besok."
        else -> "Gagal mengambil kartu dari server (HTTP ${code()})"
    }
    is SerializationException -> "Format data dari server tidak dikenali"
    else -> message ?: "Terjadi kesalahan yang tidak diketahui"
}

fun formatPrice(value: Double?): String =
    if (value == null) "N/A" else String.format(Locale.US, "\$%.2f", value)
