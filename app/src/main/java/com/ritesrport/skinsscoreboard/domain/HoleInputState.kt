package com.ritesrport.skinsscoreboard.domain

data class HoleInputState(
    val holeData: HoleData,
    val player: Player,
    val isGameOver: Boolean = false,
)