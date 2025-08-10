package com.ritesrport.skinsscoreboard.domain.repository

import com.ritesrport.skinsscoreboard.domain.entity.Player
import com.ritesrport.skinsscoreboard.domain.entity.PlayerResults

interface ResultsRepository {
    suspend fun saveNewResult(player: Player, holeNumber: Int, strokes: Int)
    suspend fun putPlayersNumber(number: Int)
    suspend fun getResults(playerNumber: Int): PlayerResults?
    suspend fun clearResults()
}