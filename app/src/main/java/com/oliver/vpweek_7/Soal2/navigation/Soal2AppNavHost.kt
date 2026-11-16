package com.oliver.vpweek_7.Soal2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.oliver.vpweek_7.Soal2.ui.screen.AlbumDetailScreen
import com.oliver.vpweek_7.Soal2.ui.screen.ArtistScreen
import com.oliver.vpweek_7.Soal2.ui.widgets.MusicArtistTheme

@Composable
fun Soal2AppNavHost() {
    MusicArtistTheme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = ArtistRoute) {
            composable<ArtistRoute> {
                ArtistScreen(onAlbumClick = { id -> navController.navigate(AlbumDetailRoute(id)) })
            }
            composable<AlbumDetailRoute> {
                AlbumDetailScreen(onBack = { navController.popBackStack() })
            }
        }
    }
}
