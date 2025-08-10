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
import com.ritesrport.skinsscoreboard.di.HoleRepositoryProvider
import com.ritesrport.skinsscoreboard.di.PlayerRepositoryProvider
import com.ritesrport.skinsscoreboard.di.ResultsRepositoryProvider
import com.ritesrport.skinsscoreboard.ui.theme.SkinsScoreboardTheme
import com.ritesrport.skinsscoreboard.view.composables.Greeting
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ritesrport.skinsscoreboard.view.view_model.MyViewModelFactory

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
        Greeting(
            viewModel = viewModel(factory = MyViewModelFactory(
                HoleRepositoryProvider.provideRepository(),
                PlayerRepositoryProvider.provideRepository(),
                ResultsRepositoryProvider.provideRepository()
            )),
            modifier = Modifier.padding(innerPadding)
        )
    }
}