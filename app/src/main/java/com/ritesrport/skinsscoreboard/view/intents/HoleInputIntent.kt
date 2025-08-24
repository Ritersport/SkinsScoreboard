package com.ritesrport.skinsscoreboard.view.intents

sealed interface HoleInputIntent {
    data class PutPlayerName(val playerNumber: Int, val name: String) : HoleInputIntent
    object CompletePlayerInput : HoleInputIntent
    data class UserChangedStrokesInput(val input: String) : HoleInputIntent
    object NewGame : HoleInputIntent
}