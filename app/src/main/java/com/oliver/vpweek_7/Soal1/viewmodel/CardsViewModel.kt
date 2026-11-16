package com.oliver.vpweek_7.Soal1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oliver.vpweek_7.Soal1.data.remote.ServiceLocator
import com.oliver.vpweek_7.Soal1.data.repository.CardRepository
import com.oliver.vpweek_7.Soal1.model.PokemonCard
import com.oliver.vpweek_7.Soal1.util.toMessage
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface CardsUiState {
    data object Loading : CardsUiState
    data class Success(val cards: List<PokemonCard>) : CardsUiState
    data class Error(val message: String) : CardsUiState
}

class CardsViewModel(
    private val repo: CardRepository = ServiceLocator.repository
) : ViewModel() {

    private val _query = MutableStateFlow("Charizard")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _uiState = MutableStateFlow<CardsUiState>(CardsUiState.Loading)
    val uiState: StateFlow<CardsUiState> = _uiState.asStateFlow()

    private val _selectedCard = MutableStateFlow<PokemonCard?>(null)
    val selectedCard: StateFlow<PokemonCard?> = _selectedCard.asStateFlow()

    private var searchJob: Job? = null

    init {
        search()
    }

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }

    fun clearQuery() {
        _query.value = ""
    }

    fun search() {
        val q = _query.value.trim()
        if (q.isEmpty()) return

        searchJob?.cancel()
        _uiState.value = CardsUiState.Loading
        searchJob = viewModelScope.launch {
            _uiState.value = repo.searchCards(q).fold(
                onSuccess = { CardsUiState.Success(it) },
                onFailure = { CardsUiState.Error(it.toMessage()) }
            )
        }
    }

    fun searchSuggestion(name: String) {
        _query.value = name
        search()
    }

    fun openCard(card: PokemonCard) {
        _selectedCard.value = card
    }

    fun closeCard() {
        _selectedCard.value = null
    }
}
