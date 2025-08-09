package com.ritesrport.skinsscoreboard.domain

data class MatchResult(
    val round: Int,
    val strokes: MutableList<PlayerStroke> = mutableListOf(),
) {
    data class PlayerStroke(val player: Player, val strokeCount: Int)

    fun addStroke(player: Player, strokeCount: Int) {
        strokes.add(PlayerStroke(player, strokeCount))
    }

    fun winner(): Player? {
        var minStroke: Int? = null
        var winner: Player? = null
        for (stroke in strokes) {
            if (minStroke == null || stroke.strokeCount < minStroke) {
                minStroke = stroke.strokeCount
                winner = stroke.player
            } else if (stroke.strokeCount == minStroke) {
                winner = null
            }
        }
        return winner
    }
/*
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MatchResult

        return strokes.contentEquals(other.strokes)
    }

    override fun hashCode(): Int {
        return strokes.contentHashCode()
    }*/
}
