package com.ritesrport.skinsscoreboard.view.intents

sealed interface HoleInputIntent {
    data class PutPlayerName(val playerNumber: Int, val name: String) : HoleInputIntent
    data class CompletePlayerInput(val enteredNumber: Int) : HoleInputIntent
    object NewGame : HoleInputIntent
}