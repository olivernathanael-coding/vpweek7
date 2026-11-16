package com.oliver.vpweek_7.Soal1.data.repository

import com.oliver.vpweek_7.Soal1.data.dto.CardDto
import com.oliver.vpweek_7.Soal1.model.PokemonCard

fun CardDto.toPokemonCard(): PokemonCard? {
    val cardId = id ?: tcgPlayerId ?: return null
    return PokemonCard(
        id = cardId,
        name = name.orEmpty(),
        setName = setName.orEmpty(),
        cardNumber = cardNumber.orEmpty(),
        rarity = rarity.orEmpty(),
        type = (pokemonType ?: cardType).orEmpty(),
        imageUrl = imageUrl400 ?: imageUrl,
        marketPrice = prices?.market,
        lowPrice = prices?.low,
        listings = prices?.listings?.toInt(),
        sellers = prices?.sellers?.toInt(),
        primaryPrinting = prices?.primaryPrinting.orEmpty()
    )
}
