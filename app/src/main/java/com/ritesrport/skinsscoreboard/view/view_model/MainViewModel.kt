package com.ritesrport.skinsscoreboard.view.view_model

import androidx.lifecycle.ViewModel
import com.ritesrport.skinsscoreboard.view.states.GameResultState
import com.ritesrport.skinsscoreboard.domain.HoleInputIntent
import com.ritesrport.skinsscoreboard.domain.HoleInputRepository
import com.ritesrport.skinsscoreboard.view.states.HoleInputState
import com.ritesrport.skinsscoreboard.domain.ResultsComparator
import com.ritesrport.skinsscoreboard.view.composables.GameResults
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking

class MainViewModel(private val repository: HoleInputRepository) : ViewModel() {
    private val _holeInputState = runBlocking {
        MutableStateFlow(
            HoleInputState(
                holeData = repository.getFirstHole(),
                player = repository.getFirstPlayer(),
            )
        )
    }

    private val _gameResultState: MutableStateFlow<GameResultState> = MutableStateFlow(GameResultState.InProgress)

    val holeInputState: StateFlow<HoleInputState> = _holeInputState
    val gameResultState: StateFlow<GameResultState> = _gameResultState

    fun processIntent(intent: HoleInputIntent) {
        when (intent) {
            is HoleInputIntent.CompletePlayerInput -> onCompletePlayerInput(intent.enteredNumber)
            is HoleInputIntent.PutPlayerName -> TODO()
            is HoleInputIntent.NewGame -> onNewGame()
        }
    }

    private fun onCompletePlayerInput(strokesNumber: Int) {
        runBlocking {
            val data = holeInputState.value
            repository.saveNewResult(data.player, data.holeData.holeNumber, strokesNumber)
            val nextPlayer = repository.getNextPlayer(holeInputState.value.player)
            if (nextPlayer != null) {
                _holeInputState.update { it.copy(player = nextPlayer) }
            } else {
                val nextHole = repository.getHoleData(holeInputState.value.holeData.holeNumber + 1)
                if (nextHole != null) {
                    _holeInputState.update {
                        HoleInputState(
                            holeData = nextHole,
                            player = repository.getFirstPlayer()
                        )
                    }
                } else {
                    setWinner()
                }
            }
        }
    }

    private suspend fun setWinner() {
        val player1 = repository.getFirstPlayer()
        val player2 = repository.getNextPlayer(player1) ?: return
        val player1Result = repository.getResults(1) ?: return
        val player2Result = repository.getResults(2) ?: return //TODO - обработка ошибок
        val scores =
            ResultsComparator.getScores(mapOf(player1 to player1Result, player2 to player2Result))
        val winner = ResultsComparator.getWinner(scores)
        val result = if (winner == null) {
            GameResultState.Draw(scores[0], scores[1])
        } else {
            GameResultState.Win(winner.player, scores[0], scores[1])
        }

        _holeInputState.update { it.copy(isGameOver = true) }
        _gameResultState.update { result }
    }

    private fun onNewGame() {
        runBlocking {
            repository.clearResults()
            _holeInputState.update {
                HoleInputState(
                    holeData = repository.getFirstHole(),
                    player = repository.getFirstPlayer()
                )
            }
            _gameResultState.update { GameResultState.InProgress }
        }
    }
}