package com.oliver.vpweek_7.Soal1.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.oliver.vpweek_7.Soal1.model.PokemonCard
import com.oliver.vpweek_7.Soal1.util.formatPrice

/** Bottom sheet detail kartu. Isinya bisa di-scroll. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDetailSheet(card: PokemonCard, onDismiss: () -> Unit) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Navy,
        contentColor = TextMain
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .background(Brush.verticalGradient(listOf(Color(0xFF4A262B), Navy)))
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .navigationBarsPadding()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = card.imageUrl,
                    contentDescription = card.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(280.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
                Spacer(Modifier.height(16.dp))
                Text(card.name, color = TextMain, fontSize = 22.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                Text(card.setName, color = TextMuted, fontSize = 15.sp, textAlign = TextAlign.Center)
                Spacer(Modifier.height(16.dp))

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    PriceBox(
                        label = "Market Price",
                        value = formatPrice(card.marketPrice),
                        accent = Mint,
                        background = MintDark,
                        modifier = Modifier.weight(1f),
                        isMarket = true
                    )
                    PriceBox(
                        label = "Lowest Price",
                        value = formatPrice(card.lowPrice),
                        accent = Gold,
                        background = GoldDark,
                        modifier = Modifier.weight(1f),
                        isMarket = false
                    )
                }

                InfoSection(
                    title = "Market Activity",
                    rows = listOf(
                        "Active Listings" to (card.listings?.toString() ?: "-"),
                        "Verified Sellers" to (card.sellers?.toString() ?: "-"),
                        "Primary Printing" to card.primaryPrinting
                    )
                )
                InfoSection(
                    title = "Card Specifications",
                    rows = listOf(
                        "Card Number" to card.cardNumber,
                        "Rarity" to card.rarity,
                        "Type" to card.type
                    )
                )
                Spacer(Modifier.height(24.dp))
            }

            // Tombol tutup (X) di kanan atas
            IconButton(
                onClick = onDismiss,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0x66000000))
            ) {
                Icon(Icons.Default.Close, contentDescription = "Tutup", tint = TextMain, modifier = Modifier.size(18.dp))
            }
        }
    }
}

@Composable
private fun PriceBox(
    label: String,
    value: String,
    accent: Color,
    background: Color,
    isMarket: Boolean,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(16.dp)
    Column(
        modifier = modifier
            .clip(shape)
            .background(background)
            .border(1.dp, accent.copy(alpha = 0.35f), shape)
            .padding(14.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = if (isMarket) Icons.AutoMirrored.Filled.TrendingUp else Icons.Default.LocalOffer,
                contentDescription = null,
                tint = accent,
                modifier = Modifier.size(18.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text(label, color = TextMuted, fontSize = 12.sp)
        }
        Spacer(Modifier.height(8.dp))
        Text(value, color = accent, fontSize = 24.sp, fontWeight = FontWeight.Bold)
    }
}
