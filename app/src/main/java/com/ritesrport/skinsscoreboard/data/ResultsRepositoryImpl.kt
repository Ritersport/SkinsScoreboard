package com.ritesrport.skinsscoreboard.data

import com.ritesrport.skinsscoreboard.domain.entity.Player
import com.ritesrport.skinsscoreboard.domain.entity.PlayerResults
import com.ritesrport.skinsscoreboard.domain.repository.ResultsRepository

class ResultsRepositoryImpl : ResultsRepository {
    private var results: Array<PlayerResults> = Array(2) { PlayerResults() }

    override suspend fun clearResults() {
        results = Array(2) { PlayerResults() }
    }

    override suspend fun putPlayersNumber(number: Int) {
        results = Array(number) { PlayerResults() }
    }

    override suspend fun getResults(playerNumber: Int): PlayerResults? {
        if (playerNumber > results.size) {
            return null
        }
        return results[playerNumber - 1]
    }

    override suspend fun saveNewResult(
        player: Player,
        holeNumber: Int,
        strokes: Int
    ) {
        results[player.number - 1].strokes[holeNumber - 1] = strokes
    }
}