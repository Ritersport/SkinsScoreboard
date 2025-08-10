package com.ritesrport.skinsscoreboard.view.states

import com.ritesrport.skinsscoreboard.domain.HoleData
import com.ritesrport.skinsscoreboard.domain.Player

data class HoleInputState(
    val holeData: HoleData,
    val player: Player,
    val isGameOver: Boolean = false,
)