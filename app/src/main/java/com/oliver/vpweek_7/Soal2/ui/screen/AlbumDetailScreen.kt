package com.oliver.vpweek_7.Soal2.ui.screen

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.oliver.vpweek_7.Soal2.model.AlbumDetail
import com.oliver.vpweek_7.Soal2.model.Track
import com.oliver.vpweek_7.Soal2.ui.widgets.formatDuration
import com.oliver.vpweek_7.Soal2.ui.widgets.ErrorView
import com.oliver.vpweek_7.Soal2.ui.widgets.ExpandableText
import com.oliver.vpweek_7.Soal2.ui.widgets.LoadingView
import com.oliver.vpweek_7.Soal2.ui.widgets.Bg
import com.oliver.vpweek_7.Soal2.ui.widgets.Green
import com.oliver.vpweek_7.Soal2.ui.widgets.GreenDark
import com.oliver.vpweek_7.Soal2.ui.widgets.Surface1
import com.oliver.vpweek_7.Soal2.ui.widgets.Surface2
import com.oliver.vpweek_7.Soal2.ui.widgets.TextPrimary
import com.oliver.vpweek_7.Soal2.ui.widgets.TextSecondary
import com.oliver.vpweek_7.Soal2.viewmodel.AlbumDetailViewModel
import com.oliver.vpweek_7.Soal2.viewmodel.AlbumUiState

@Composable
fun AlbumDetailScreen(
    onBack: () -> Unit,
    viewModel: AlbumDetailViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Bg)
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali", tint = TextPrimary)
            }
            Text("Detail Album", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }

        when (val s = state) {
            is AlbumUiState.Loading -> LoadingView("Memuat detail album & track list...")
            is AlbumUiState.Error -> ErrorView(message = s.message, onRetry = viewModel::loadAlbum)
            is AlbumUiState.Success -> AlbumContent(s.detail)
        }
    }
}

@Composable
private fun AlbumContent(detail: AlbumDetail) {
    val album = detail.album

    LazyColumn(
        modifier = Modifier.fillMaxSize().navigationBarsPadding(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp)
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = album.cover,
                    contentDescription = album.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(220.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Surface2)
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    album.name,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center
                )
                Text(album.artist, color = Green, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (album.year.isNotBlank()) Chip("Tahun ${album.year}")
                    if (album.genre.isNotBlank()) Chip(album.genre)
                }
            }
        }

        item {
            Column(Modifier.padding(top = 20.dp)) {
                Text("Deskripsi Album", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Spacer(Modifier.height(8.dp))
                ExpandableText(
                    text = album.description.ifBlank { "Deskripsi belum tersedia." },
                    collapsedLines = 3
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 24.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.MusicNote, contentDescription = null, tint = Green, modifier = Modifier.size(20.dp))
                    Text(
                        "Daftar Lagu",
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(start = 6.dp)
                    )
                }
                Text(
                    "${detail.tracks.size} Lagu",
                    color = Green,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(GreenDark)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }

        if (detail.tracks.isEmpty()) {
            item { Text("Daftar lagu belum tersedia.", color = TextSecondary, fontSize = 13.sp) }
        } else {
            items(detail.tracks, key = { it.id }) { track ->
                TrackRow(track)
                HorizontalDivider(color = Surface1)
            }
        }
    }
}

@Composable
private fun Chip(text: String) {
    Text(
        text = text,
        color = TextSecondary,
        fontSize = 11.sp,
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(Surface1)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    )
}

@Composable
private fun TrackRow(track: Track) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "%02d".format(track.number),
            color = Green,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            modifier = Modifier.padding(end = 16.dp)
        )
        Column(Modifier.weight(1f)) {
            Text(track.title, color = TextPrimary, fontWeight = FontWeight.Medium, fontSize = 14.sp)
            if (track.genre.isNotBlank()) {
                Text(track.genre, color = TextSecondary, fontSize = 11.sp)
            }
        }
        Icon(Icons.Default.Schedule, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(12.dp))
        Text(
            formatDuration(track.durationMs),
            color = TextSecondary,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}
