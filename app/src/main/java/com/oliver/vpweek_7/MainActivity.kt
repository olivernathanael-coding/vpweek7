package com.oliver.vpweek_7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.oliver.vpweek_7.Soal1.Soal1App
import com.oliver.vpweek_7.Soal2.navigation.Soal2AppNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Pilih soal: comment salah satu, uncomment yang mau dijalankan
            Soal1App()          // Soal 1 - Pokemon Card
            // Soal2AppNavHost()   // Soal 2 - Music Artist
        }
    }
}
