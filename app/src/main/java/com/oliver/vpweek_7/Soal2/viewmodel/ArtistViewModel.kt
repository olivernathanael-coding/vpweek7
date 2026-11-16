package com.oliver.vpweek_7.Soal2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oliver.vpweek_7.Soal2.model.ArtistPage
import com.oliver.vpweek_7.Soal2.data.remote.ServiceLocator
import com.oliver.vpweek_7.Soal2.data.repository.MusicRepository
import com.oliver.vpweek_7.Soal2.util.toMessage
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface ArtistUiState {
    data object Loading : ArtistUiState
    data class Success(val page: ArtistPage) : ArtistUiState
    data class Error(val message: String) : ArtistUiState
}

class ArtistViewModel(
    private val repo: MusicRepository = ServiceLocator.repository
) : ViewModel() {

    private val _query = MutableStateFlow("Coldplay")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _uiState = MutableStateFlow<ArtistUiState>(ArtistUiState.Loading)
    val uiState: StateFlow<ArtistUiState> = _uiState.asStateFlow()

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
        _uiState.value = ArtistUiState.Loading
        searchJob = viewModelScope.launch {
            _uiState.value = repo.getArtistPage(q).fold(
                onSuccess = { ArtistUiState.Success(it) },
                onFailure = { ArtistUiState.Error(it.toMessage()) }
            )
        }
    }
}
