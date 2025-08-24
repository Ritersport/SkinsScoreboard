package com.ritesrport.skinsscoreboard.view.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ritesrport.skinsscoreboard.view.states.GameResultState

@Composable
fun GameResults(
    gameResultState: GameResultState,
    onNewGameClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        modifier = modifier.widthIn(min = 300.dp)
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .wrapContentHeight()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            when (gameResultState) {
                is GameResultState.Draw -> {
                    Draw(gameResultState, modifier)
                }

                GameResultState.InProgress -> {
                    onNewGameClicked
                }

                is GameResultState.Win -> {
                    Winner(gameResultState, modifier)
                }

                is GameResultState.Error -> Text(
                    text = "Something went wrong: ${gameResultState.msg}",
                    fontSize = 24.sp,
                    modifier = modifier.padding(8.dp)
                )
            }
            OutlinedButton(onClick = onNewGameClicked) {
                Text("New game")
            }
        }
    }
}

@Composable
fun Winner(
    winResultState: GameResultState.Win,
    modifier: Modifier = Modifier
) {
    Text(
        text = "Winner ${winResultState.winner.name}!!!",
        fontSize = 24.sp,
        modifier = modifier.padding(8.dp)
    )
    Text("${winResultState.player1Score.player.name}: ${winResultState.player1Score.score}")
    Text("${winResultState.player2Score.player.name}: ${winResultState.player2Score.score}")
}

@Composable
fun Draw(
    drawResultState: GameResultState.Draw,
    modifier: Modifier = Modifier
) {
    Text(
        text = "Draw!",
        fontSize = 24.sp,
        modifier = modifier.padding(8.dp)
    )
    Text("${drawResultState.player1Score.player.name}: ${drawResultState.player1Score.score}")
    Text("${drawResultState.player2Score.player.name}: ${drawResultState.player2Score.score}")
}