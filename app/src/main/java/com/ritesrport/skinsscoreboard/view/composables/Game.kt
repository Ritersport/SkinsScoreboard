package com.ritesrport.skinsscoreboard.view.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.ritesrport.skinsscoreboard.R
import com.ritesrport.skinsscoreboard.view.intents.HoleInputIntent.*
import com.ritesrport.skinsscoreboard.view.states.GameResultState
import com.ritesrport.skinsscoreboard.view.view_model.MainViewModel

@Composable
fun Game(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.Center) {
        Image(
            painterResource(R.drawable.hole),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier.fillMaxHeight()
        )
        val resultState by viewModel.gameResultState.collectAsState()
        when (resultState) {
            is GameResultState.Win, is GameResultState.Draw, is GameResultState.Error -> {
                GameResults(
                    resultState,
                    { viewModel.processIntent(NewGame) },
                    modifier
                )
            }

            is GameResultState.InProgress -> {
                HoleInput(
                    viewModel.holeInputState.collectAsState().value,
                    { viewModel.processIntent(UserChangedStrokesInput(it))},
                    { viewModel.processIntent(CompletePlayerInput) },
                    modifier
                )
            }
        }
    }
}