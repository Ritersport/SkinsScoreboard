package com.ritesrport.skinsscoreboard.domain

import com.ritesrport.skinsscoreboard.domain.GameConstants.MATCH_COUNT
import com.ritesrport.skinsscoreboard.domain.entity.MatchResult
import com.ritesrport.skinsscoreboard.domain.entity.Player
import com.ritesrport.skinsscoreboard.domain.entity.PlayerResults
import com.ritesrport.skinsscoreboard.domain.entity.PlayerScore
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.iterator

object ResultsComparator {


    fun getScores(results: Map<Player, PlayerResults>): List<PlayerScore> {
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
        return playerToScore.map { PlayerScore(it.key, it.value) }
    }

    /**
     * @return null, если ничья
     */
    fun getWinner(scores: List<PlayerScore>): PlayerScore? {
        var maxScore = scores.firstOrNull()?.score ?: throw Exception("No results found")
        var winner: Player? = scores.firstOrNull()?.player ?: throw Exception("No results found")
        for (i in 1.. scores.size - 1) {
            if (maxScore < scores[i].score) {
                maxScore = scores[i].score
                winner = scores[i].player
            }
            else if (maxScore == scores[i].score) {
                winner = null
            }
        }
        return winner?.let { PlayerScore(winner, maxScore) }
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