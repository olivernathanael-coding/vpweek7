package com.oliver.vpweek_7.Soal2.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.oliver.vpweek_7.Soal2.model.Artist
import com.oliver.vpweek_7.Soal2.ui.widgets.AlbumCard
import com.oliver.vpweek_7.Soal2.ui.widgets.AppHeader
import com.oliver.vpweek_7.Soal2.ui.widgets.ErrorView
import com.oliver.vpweek_7.Soal2.ui.widgets.ExpandableText
import com.oliver.vpweek_7.Soal2.ui.widgets.LoadingView
import com.oliver.vpweek_7.Soal2.ui.widgets.SearchBar
import com.oliver.vpweek_7.Soal2.ui.widgets.Bg
import com.oliver.vpweek_7.Soal2.ui.widgets.Green
import com.oliver.vpweek_7.Soal2.ui.widgets.GreenDark
import com.oliver.vpweek_7.Soal2.ui.widgets.Surface2
import com.oliver.vpweek_7.Soal2.ui.widgets.TextPrimary
import com.oliver.vpweek_7.Soal2.ui.widgets.TextSecondary
import com.oliver.vpweek_7.Soal2.viewmodel.ArtistUiState
import com.oliver.vpweek_7.Soal2.viewmodel.ArtistViewModel

@Composable
fun ArtistScreen(
    onAlbumClick: (String) -> Unit,
    viewModel: ArtistViewModel = viewModel()
) {
    val query by viewModel.query.collectAsStateWithLifecycle()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Bg)
            .statusBarsPadding()
    ) {
        AppHeader()
        SearchBar(
            query = query,
            onQueryChange = viewModel::onQueryChange,
            onClear = viewModel::clearQuery,
            onSearch = viewModel::search
        )
        Box(Modifier.fillMaxSize().padding(top = 12.dp)) {
            when (val s = state) {
                is ArtistUiState.Loading -> LoadingView("Mencari artis...")
                is ArtistUiState.Error -> ErrorView(message = s.message, onRetry = viewModel::search)
                is ArtistUiState.Success -> {
                    val artist = s.page.artist
                    val albums = s.page.albums

                    // Seluruh halaman (banner, bio, album) ikut scroll dalam 1 grid
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 24.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxSize().navigationBarsPadding()
                    ) {
                        item(span = { GridItemSpan(maxLineSpan) }) { ArtistBanner(artist) }

                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Column {
                                Text("Tentang Artis", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                Box(Modifier.height(8.dp))
                                ExpandableText(
                                    text = artist.biography.ifBlank { "Biografi belum tersedia." }
                                )
                            }
                        }

                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text("Diskografi Album", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                    Text("Koleksi rilisan resmi", color = TextSecondary, fontSize = 11.sp)
                                }
                                Text(
                                    text = "${albums.size} Album",
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

                        if (albums.isEmpty()) {
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                Text("Album belum tersedia.", color = TextSecondary, fontSize = 13.sp)
                            }
                        } else {
                            items(albums, key = { it.id }) { album ->
                                AlbumCard(album = album, onClick = { onAlbumClick(album.id) })
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ArtistBanner(artist: Artist) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Surface2)
    ) {
        AsyncImage(
            model = artist.banner,
            contentDescription = artist.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(Color.Transparent, Bg.copy(alpha = 0.95f))))
        )
        Row(
            modifier = Modifier.align(Alignment.BottomStart).padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = artist.thumb,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(52.dp).clip(CircleShape).background(Surface2)
            )
            Column(Modifier.padding(start = 12.dp)) {
                Text(artist.name, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 22.sp)
                Row {
                    Text(artist.genre, color = Green, fontSize = 11.sp, fontWeight = FontWeight.Medium)
                    if (artist.country.isNotBlank()) {
                        Text("  •  ${artist.country}", color = TextSecondary, fontSize = 11.sp)
                    }
                }
            }
        }
    }
}
