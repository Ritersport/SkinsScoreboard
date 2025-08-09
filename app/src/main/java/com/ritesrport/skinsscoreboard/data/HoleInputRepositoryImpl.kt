package com.ritesrport.skinsscoreboard.data

import com.ritesrport.skinsscoreboard.domain.HoleData
import com.ritesrport.skinsscoreboard.domain.HoleInputRepository
import com.ritesrport.skinsscoreboard.domain.Player
import com.ritesrport.skinsscoreboard.domain.PlayerResults

class HoleInputRepositoryImpl : HoleInputRepository {

    private val holePars = arrayOf(4, 5, 3/*, 5, 4, 3, 4, 5, 4, 4, 5, 3, 4, 4, 3, 4, 4, 4*/)
    private var playerNames: Array<String> = Array(2) { getDefaultPlayerName(it) }
    private var results: Array<PlayerResults> = Array(2) { PlayerResults() }

    override suspend fun clearResults() {
        playerNames = Array(2) { getDefaultPlayerName(it) }
        results = Array(2) { PlayerResults() }
    }

    override suspend fun getResults(playerNumber: Int): PlayerResults? {
        if (playerNumber > results.size) {
            return null
        }
        return results[playerNumber - 1]
    }

    override suspend fun getHoleData(holeNumber: Int): HoleData? {
        if (holeNumber > holePars.size) {
            return null
        }
        return HoleData(holeNumber, holePars[holeNumber - 1])
    }

    override suspend fun getFirstHole(): HoleData {
        return HoleData(1, holePars[0])
    }

    override suspend fun getPlayerName(number: Int): String? {
        return if (number in 1..playerNames.size) {
            playerNames[number - 1]
        } else {
            null
        }
    }

    override suspend fun putPlayersNumber(number: Int) {
        if (validatePlayersNumber(number)) {
            playerNames = Array(number) { getDefaultPlayerName(it) }
            results = Array(number) { PlayerResults() }
        }
    }

    override suspend fun putPlayerName(number: Int, name: String) {
        if (number in 1..playerNames.size) {
            playerNames[number - 1] = name
        }
    }

    override suspend fun getNextPlayer(currentPlayer: Player): Player? {
        if (currentPlayer.number == playerNames.size){
            return null
        }
        return Player(currentPlayer.number + 1, playerNames[currentPlayer.number])
    }

    override suspend fun getFirstPlayer(): Player {
        return Player(1, playerNames[0])
    }

    override suspend fun saveNewResult(
        player: Player,
        holeNumber: Int,
        strokes: Int
    ) {
        results[player.number - 1].strokes[holeNumber - 1] = strokes
    }

    private fun validatePlayersNumber(number: Int) = number in 2..4

    private fun getDefaultPlayerName(idx: Int) = "PLAYER_${idx + 1}"
}