package com.ritesrport.skinsscoreboard.domain

interface HoleInputRepository {
    suspend fun getHoleData(holeNumber: Int): HoleData?
    suspend fun getFirstHole(): HoleData

    suspend fun getPlayerName(number: Int): String?
    suspend fun putPlayersNumber(number: Int)
    suspend fun putPlayerName(number: Int, name: String)

    suspend fun getNextPlayer(currentPlayer: Player): Player?
    suspend fun getFirstPlayer(): Player
    suspend fun saveNewResult(player: Player, holeNumber: Int, strokes: Int)

    suspend fun getResults(playerNumber: Int): PlayerResults?
    suspend fun clearResults()
}