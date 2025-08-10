package com.ritesrport.skinsscoreboard.domain.repository

import com.ritesrport.skinsscoreboard.domain.entity.Player

interface PlayerRepository {
    suspend fun putPlayersNumber(number: Int)
    suspend fun putPlayerName(number: Int, name: String)
    suspend fun getNextPlayer(currentPlayer: Player): Player?
    suspend fun getFirstPlayer(): Player
    suspend fun clearPlayers()
}