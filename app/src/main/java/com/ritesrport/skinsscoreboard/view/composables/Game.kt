package com.ritesrport.skinsscoreboard.view.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.ritesrport.skinsscoreboard.view.view_model.MainViewModel

@Composable
fun Game(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    val state by viewModel.holeInputState.collectAsState()
    if (state.isGameOver) {
        GameResults(viewModel, modifier)
    }
    else {
        HoleInput(viewModel, modifier)
    }
}