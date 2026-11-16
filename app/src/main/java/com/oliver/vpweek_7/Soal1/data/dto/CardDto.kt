package com.oliver.vpweek_7.Soal1.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CardsResponse(
    val data: List<CardDto>? = null
)

@Serializable
data class CardDto(
    val id: String? = null,
    val tcgPlayerId: String? = null,
    val name: String? = null,
    val setName: String? = null,
    val cardNumber: String? = null,
    val rarity: String? = null,
    val cardType: String? = null,
    val pokemonType: String? = null,
    val prices: PricesDto? = null,
    @SerialName("imageCdnUrl400") val imageUrl400: String? = null,
    @SerialName("imageCdnUrl") val imageUrl: String? = null
)

@Serializable
data class PricesDto(
    val market: Double? = null,
    val low: Double? = null,
    val listings: Double? = null,
    val sellers: Double? = null,
    val primaryPrinting: String? = null
)
