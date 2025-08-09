package com.ritesrport.skinsscoreboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.ritesrport.skinsscoreboard.data.HoleInputRepositoryImpl
import com.ritesrport.skinsscoreboard.di.HoleInputRepositoryProvider
import com.ritesrport.skinsscoreboard.ui.theme.SkinsScoreboardTheme
import com.ritesrport.skinsscoreboard.view.composables.Game
import com.ritesrport.skinsscoreboard.view.composables.HoleInput
import com.ritesrport.skinsscoreboard.view.view_model.MainViewModel
import com.ritesrport.skinsscoreboard.view.view_model.ViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SkinsScoreboardTheme {
                App()
            }
        }
    }
}

@Composable
fun App(modifier: Modifier = Modifier) {
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Game(
            MainViewModel(HoleInputRepositoryProvider.provideRepository()),
            modifier = Modifier.padding(innerPadding)
        )
    }
}