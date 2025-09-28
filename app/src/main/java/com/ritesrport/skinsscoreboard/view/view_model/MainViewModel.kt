package com.ritesrport.skinsscoreboard.view.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ritesrport.skinsscoreboard.view.states.GameResultState
import com.ritesrport.skinsscoreboard.view.intents.HoleInputIntent
import com.ritesrport.skinsscoreboard.domain.repository.HoleRepository
import com.ritesrport.skinsscoreboard.view.states.HoleInputState
import com.ritesrport.skinsscoreboard.domain.ResultsComparator
import com.ritesrport.skinsscoreboard.domain.repository.PlayerRepository
import com.ritesrport.skinsscoreboard.domain.repository.ResultsRepository
import com.ritesrport.skinsscoreboard.view.states.HoleInputValidationState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val holeRepository: HoleRepository,
    private val playerRepository: PlayerRepository,
    private val resultsRepository: ResultsRepository
) : ViewModel() {
    private val _holeInputState: MutableStateFlow<HoleInputState?> = MutableStateFlow(null)
    private val _gameResultState: MutableStateFlow<GameResultState> =
        MutableStateFlow(GameResultState.InProgress)

    val holeInputState: StateFlow<HoleInputState?> = _holeInputState
    val gameResultState: StateFlow<GameResultState> = _gameResultState

    init {
        viewModelScope.launch {
            val firstHole = holeRepository.getFirstHole()
            val firstPlayer = playerRepository.getFirstPlayer()
            _holeInputState.value = HoleInputState(
                inputValidation = HoleInputValidationState.Invalid,
                holeData = firstHole,
                player = firstPlayer
            )
        }
    }

    fun processIntent(intent: HoleInputIntent) {
        when (intent) {
            is HoleInputIntent.CompletePlayerInput -> onCompletePlayerInput()
            is HoleInputIntent.PutPlayerName -> TODO()
            is HoleInputIntent.NewGame -> onNewGame()
            is HoleInputIntent.UserChangedStrokesInput -> onUserEnteredStrokes(intent.input)
        }
    }

    private fun onUserEnteredStrokes(input: String) {
        val intValue = input.toIntOrNull()
        val isValid = intValue in 1..100
        _holeInputState.update {
            it?.copy(
                if (isValid || input.isEmpty()) input else it.holeInput,
                if (isValid) {
                    HoleInputValidationState.Valid(intValue!!)
                } else {
                    if (input.isEmpty()) {
                        HoleInputValidationState.Invalid
                    } else {
                        it.inputValidation
                    }
                }
            )
        }
    }

    private fun onCompletePlayerInput() {
        viewModelScope.launch {
            val data = holeInputState.value
            if (data?.inputValidation is HoleInputValidationState.Valid) {
                resultsRepository.saveNewResult(
                    data.player, data.holeData.holeNumber, data.inputValidation.validatedInput
                )

                val nextPlayer = playerRepository.getNextPlayer(data.player)
                if (nextPlayer != null) {
                    _holeInputState.update {
                        it?.copy(
                            holeInput = "",
                            inputValidation = HoleInputValidationState.Invalid,
                            player = nextPlayer
                        )
                    }
                } else {
                    val nextHole = holeRepository.getHoleData(data.holeData.holeNumber + 1)
                    if (nextHole != null) {
                        _holeInputState.update {
                            HoleInputState(
                                inputValidation = HoleInputValidationState.Invalid,
                                holeData = nextHole,
                                player = playerRepository.getFirstPlayer()
                            )
                        }
                    } else {
                        setWinner()
                    }
                }
            }
        }
    }

    private suspend fun setWinner() {
        val player1 = playerRepository.getFirstPlayer()
        val player2 = playerRepository.getNextPlayer(player1)
        val player1Result = resultsRepository.getResults(1)
        val player2Result = resultsRepository.getResults(2)

        val result = if (player2 == null || player1Result == null || player2Result == null) {
            GameResultState.Error("Results not found")
        } else {
            val scores = ResultsComparator.getScores(
                mapOf(
                    player1 to player1Result, player2 to player2Result
                )
            )
            val winner = ResultsComparator.getWinner(scores)
            if (winner == null) {
                GameResultState.Draw(scores[0], scores[1])
            } else {
                GameResultState.Win(winner.player, scores[0], scores[1])
            }
        }

        _gameResultState.update { result }
    }

    private fun onNewGame() {
        viewModelScope.launch {
            resultsRepository.clearResults()
            playerRepository.clearPlayers()
            _holeInputState.update {
                HoleInputState(
                    inputValidation = HoleInputValidationState.Invalid,
                    holeData = holeRepository.getFirstHole(),
                    player = playerRepository.getFirstPlayer()
                )
            }
            _gameResultState.update { GameResultState.InProgress }
        }
    }
}