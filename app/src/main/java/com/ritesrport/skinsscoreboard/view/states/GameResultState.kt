package com.ritesrport.skinsscoreboard.view.states

import com.ritesrport.skinsscoreboard.domain.entity.Player
import com.ritesrport.skinsscoreboard.domain.entity.PlayerScore


sealed interface GameResultState {
    data class Win(
        val winner: Player,
        val player1Score: PlayerScore,
        val player2Score: PlayerScore
    ) : GameResultState

    data class Draw(val player1Score: PlayerScore, val player2Score: PlayerScore) : GameResultState
    object InProgress : GameResultState
    data class Error(val msg: String) : GameResultState
}