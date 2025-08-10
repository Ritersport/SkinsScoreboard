package com.ritesrport.skinsscoreboard.data

import com.ritesrport.skinsscoreboard.domain.repository.PlayerRepository
import com.ritesrport.skinsscoreboard.domain.entity.Player

class PlayerRepositoryImpl : PlayerRepository {
    private var playerNames: Array<String> = Array(2) { getDefaultPlayerName(it) }

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

    override suspend fun clearPlayers() {
        playerNames = Array(2) { getDefaultPlayerName(it) }
    }

    private fun validatePlayersNumber(number: Int) = number in 2..4

    private fun getDefaultPlayerName(idx: Int) = "PLAYER_${idx + 1}"

}