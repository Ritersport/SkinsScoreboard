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
                    Text(
                        text = "Draw!",
                        fontSize = 24.sp,
                        modifier = modifier.padding(8.dp)
                    )
                    Text("${gameResultState.player1Score.player.name}: ${gameResultState.player1Score.score}")
                    Text("${gameResultState.player2Score.player.name}: ${gameResultState.player2Score.score}")
                }

                GameResultState.InProgress -> {
                    onNewGameClicked
                }

                is GameResultState.Win -> {
                    Text(
                        text = "Winner ${gameResultState.winner.name}!!!",
                        fontSize = 24.sp,
                        modifier = modifier.padding(8.dp)
                    )
                    Text("${gameResultState.player1Score.player.name}: ${gameResultState.player1Score.score}")
                    Text("${gameResultState.player2Score.player.name}: ${gameResultState.player2Score.score}")
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