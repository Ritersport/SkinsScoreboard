package com.ritesrport.skinsscoreboard.view.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
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
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .wrapContentHeight()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = if (state.winner == null) {
                    "Draw!"
                }
                else {
                    "Winner ${state.winner?.player?.name}!!!"
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