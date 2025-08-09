package com.ritesrport.skinsscoreboard.domain

sealed class HoleInputIntent {
    data class PutPlayerName(val playerNumber: Int, val name: String) : HoleInputIntent()
    data class CompletePlayerInput(val enteredNumber: Int) : HoleInputIntent()
    object NewGame : HoleInputIntent()
}