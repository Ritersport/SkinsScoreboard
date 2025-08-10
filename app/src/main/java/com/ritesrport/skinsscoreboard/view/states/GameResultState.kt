package com.ritesrport.skinsscoreboard.view.states

import com.ritesrport.skinsscoreboard.domain.Player
import com.ritesrport.skinsscoreboard.domain.PlayerScore


sealed interface GameResultState {
    data class Win(val winner: Player, val player1Score: PlayerScore, val player2Score: PlayerScore) : GameResultState
    data class Draw(val player1Score: PlayerScore, val player2Score: PlayerScore) : GameResultState
    object InProgress : GameResultState
}