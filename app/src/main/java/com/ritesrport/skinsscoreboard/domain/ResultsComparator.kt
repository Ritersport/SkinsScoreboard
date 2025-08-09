package com.ritesrport.skinsscoreboard.domain

import com.ritesrport.skinsscoreboard.domain.GameConstants.MATCH_COUNT
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.iterator

object ResultsComparator {

    /**
     * @return null, если ничья
     */
    fun getWinner(results: Map<Player, PlayerResults>): Player? {
        val playerToScore = results.keys.associateWith { 0 }.toMutableMap()

        var drawCounter = 0 //ничья
        results.toMatchResults().forEach { matchResult ->
            val matchWinner = matchResult.winner()
            if (matchWinner != null) {
                playerToScore[matchWinner] = playerToScore[matchWinner]!!.plus(drawCounter + 1)
                drawCounter = 0
            } else {
                drawCounter = drawCounter + 1
            }
        }

        if (playerToScore.values.all { it == 0 }) return null //никто не набрал очки
        val nonZeroScore = playerToScore.values.filter { it != 0 }
        if (nonZeroScore.size != nonZeroScore.toSet().size) return null //возвращаем null, если есть ненеулевые

        return playerToScore.maxByOrNull { it.value }?.key
    }


    private fun Map<Player, PlayerResults>.toMatchResults(): List<MatchResult> {
        val results = Array<MatchResult>(size = MATCH_COUNT) { i -> MatchResult(i) }
        for ((player, playerResults) in this) {
            for ((idx, matchResult) in playerResults.strokes.withIndex()) {
                results[idx].addStroke(player, matchResult)
            }
        }
        return results.toList()
    }

}