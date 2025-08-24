package com.ritesrport.skinsscoreboard.view.states

import com.ritesrport.skinsscoreboard.domain.entity.HoleData
import com.ritesrport.skinsscoreboard.domain.entity.Player

data class HoleInputState(
    val holeInput: String = "",
    val inputValidation: HoleInputValidationState,
    val holeData: HoleData,
    val player: Player,
)

sealed interface HoleInputValidationState {
    object Invalid : HoleInputValidationState
    class Valid(val validatedInput: Int): HoleInputValidationState
}