package com.ritesrport.skinsscoreboard.view.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ritesrport.skinsscoreboard.domain.HoleInputIntent
import com.ritesrport.skinsscoreboard.view.view_model.MainViewModel

@Composable
fun GameResults(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    val state by viewModel.gameResultState.collectAsState()
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(vertical = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = if (state.winner == null) {
                    "Draw!"
                }
                else {
                    "Winner ${state.winner?.name}!!!"
                },
                fontSize = 24.sp,
                modifier = modifier.padding(8.dp)
            )


            OutlinedButton(onClick = {
                viewModel.processIntent(HoleInputIntent.NewGame)
            }) {
                Text("New game")
            }
        }
    }
}