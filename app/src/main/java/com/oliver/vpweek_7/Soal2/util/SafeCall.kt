package com.oliver.vpweek_7.Soal2.util

import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import java.io.IOException

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
    is NotFoundException -> message ?: "Data tidak ditemukan"
    is IOException -> "Koneksi gagal. Periksa koneksi internet kamu."
    is HttpException -> if (code() == 429) {
        "Terlalu banyak request ke server. Coba lagi beberapa saat."
    } else {
        "Data tidak ditemukan atau server bermasalah (HTTP ${code()})"
    }
    is SerializationException -> "Format data dari server tidak dikenali"
    else -> message ?: "Terjadi kesalahan yang tidak diketahui"
}
