package com.oliver.vpweek_7.Soal1.model

data class PokemonCard(
    val id: String,
    val name: String,
    val setName: String,
    val cardNumber: String,
    val rarity: String,
    val type: String,
    val imageUrl: String?,
    val marketPrice: Double?,
    val lowPrice: Double?,
    val listings: Int?,
    val sellers: Int?,
    val primaryPrinting: String
)
