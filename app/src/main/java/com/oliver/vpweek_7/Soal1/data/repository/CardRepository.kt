package com.oliver.vpweek_7.Soal1.data.repository

import com.oliver.vpweek_7.Soal1.data.remote.PokemonApiService
import com.oliver.vpweek_7.Soal1.model.PokemonCard
import com.oliver.vpweek_7.Soal1.util.NotFoundException
import com.oliver.vpweek_7.Soal1.util.safeCall
import java.util.concurrent.ConcurrentHashMap

class CardRepository(
    private val api: PokemonApiService
) {

    private val cache = ConcurrentHashMap<String, List<PokemonCard>>()

    suspend fun searchCards(query: String): Result<List<PokemonCard>> = safeCall {
        val key = query.trim().lowercase()
        cache[key] ?: run {
            val cards = api.searchCards(search = query.trim(), limit = CARD_LIMIT)
                .data.orEmpty()
                .mapNotNull { it.toPokemonCard() }
            if (cards.isEmpty()) throw NotFoundException("Kartu \"${query.trim()}\" tidak ditemukan")
            cache[key] = cards
            cards
        }
    }

    companion object {
        const val CARD_LIMIT = 10
    }
}
