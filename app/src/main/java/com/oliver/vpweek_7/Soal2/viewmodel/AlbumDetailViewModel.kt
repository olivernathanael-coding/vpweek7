package com.oliver.vpweek_7.Soal2.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.oliver.vpweek_7.Soal2.data.remote.ServiceLocator
import com.oliver.vpweek_7.Soal2.model.AlbumDetail
import com.oliver.vpweek_7.Soal2.navigation.AlbumDetailRoute
import com.oliver.vpweek_7.Soal2.util.toMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface AlbumUiState {
    data object Loading : AlbumUiState
    data class Success(val detail: AlbumDetail) : AlbumUiState
    data class Error(val message: String) : AlbumUiState
}

class AlbumDetailViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val repo = ServiceLocator.repository

    private val albumId: String = savedStateHandle.toRoute<AlbumDetailRoute>().albumId

    private val _uiState = MutableStateFlow<AlbumUiState>(AlbumUiState.Loading)
    val uiState: StateFlow<AlbumUiState> = _uiState.asStateFlow()

    init {
        loadAlbum()
    }

    fun loadAlbum() {
        _uiState.value = AlbumUiState.Loading
        viewModelScope.launch {
            _uiState.value = repo.getAlbumDetail(albumId).fold(
                onSuccess = { AlbumUiState.Success(it) },
                onFailure = { AlbumUiState.Error(it.toMessage()) }
            )
        }
    }
}
