package com.oliver.vpweek_7.Soal1.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.oliver.vpweek_7.Soal1.ui.widgets.AppHeader
import com.oliver.vpweek_7.Soal1.ui.widgets.CardDetailSheet
import com.oliver.vpweek_7.Soal1.ui.widgets.ErrorView
import com.oliver.vpweek_7.Soal1.ui.widgets.LoadingView
import com.oliver.vpweek_7.Soal1.ui.widgets.Navy
import com.oliver.vpweek_7.Soal1.ui.widgets.PokemonCardItem
import com.oliver.vpweek_7.Soal1.ui.widgets.SearchField
import com.oliver.vpweek_7.Soal1.ui.widgets.SuggestionChips
import com.oliver.vpweek_7.Soal1.ui.widgets.TextMain
import com.oliver.vpweek_7.Soal1.ui.widgets.TextMuted
import com.oliver.vpweek_7.Soal1.viewmodel.CardsUiState
import com.oliver.vpweek_7.Soal1.viewmodel.CardsViewModel

private val Suggestions = listOf("Charizard", "Pikachu", "Mewtwo", "Gengar", "Eevee", "Mew")

@Composable
fun CardsScreen(viewModel: CardsViewModel = viewModel()) {
    val query by viewModel.query.collectAsStateWithLifecycle()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val selectedCard by viewModel.selectedCard.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Navy)
            .statusBarsPadding()
    ) {
        AppHeader()
        SearchField(
            query = query,
            onQueryChange = viewModel::onQueryChange,
            onClear = viewModel::clearQuery,
            onSearch = viewModel::search
        )
        Spacer(Modifier.height(14.dp))
        SuggestionChips(items = Suggestions, onClick = viewModel::searchSuggestion)
        Spacer(Modifier.height(8.dp))

        Box(Modifier.fillMaxSize()) {
            when (val s = state) {
                is CardsUiState.Loading -> LoadingView("Mengambil data kartu...")
                is CardsUiState.Error -> ErrorView(message = s.message, onRetry = viewModel::search)
                is CardsUiState.Success -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 24.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize().navigationBarsPadding()
                    ) {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Market Results (${s.cards.size})", color = TextMain, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                Text("USD · Near Mint", color = TextMuted, fontSize = 11.sp)
                            }
                        }
                        items(s.cards, key = { it.id }) { card ->
                            PokemonCardItem(card = card, onClick = { viewModel.openCard(card) })
                        }
                    }
                }
            }
        }
    }

    selectedCard?.let { card ->
        CardDetailSheet(card = card, onDismiss = viewModel::closeCard)
    }
}
