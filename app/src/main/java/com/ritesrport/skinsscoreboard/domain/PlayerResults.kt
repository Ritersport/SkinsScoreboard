package com.ritesrport.skinsscoreboard.domain

import com.ritesrport.skinsscoreboard.domain.ResultsComparator.MATCH_COUNT

data class PlayerResults(val strokes: Array<Int> = Array(MATCH_COUNT, {0})) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PlayerResults

        return strokes.contentEquals(other.strokes)
    }

    override fun hashCode(): Int {
        return strokes.contentHashCode()
    }
}
