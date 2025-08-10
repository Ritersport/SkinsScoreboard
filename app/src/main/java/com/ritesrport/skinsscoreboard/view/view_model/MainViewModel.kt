package com.ritesrport.skinsscoreboard.view.view_model

import androidx.lifecycle.ViewModel
import com.ritesrport.skinsscoreboard.view.states.GameResultState
import com.ritesrport.skinsscoreboard.view.intents.HoleInputIntent
import com.ritesrport.skinsscoreboard.domain.repository.HoleRepository
import com.ritesrport.skinsscoreboard.view.states.HoleInputState
import com.ritesrport.skinsscoreboard.domain.ResultsComparator
import com.ritesrport.skinsscoreboard.domain.repository.PlayerRepository
import com.ritesrport.skinsscoreboard.domain.repository.ResultsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking

class MainViewModel(
    private val holeRepository: HoleRepository,
    private val playerRepository: PlayerRepository,
    private val resultsRepository: ResultsRepository
    ) : ViewModel() {
    private val _holeInputState = runBlocking {
        MutableStateFlow(
            HoleInputState(
                holeData = holeRepository.getFirstHole(),
                player = playerRepository.getFirstPlayer(),
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
            resultsRepository.saveNewResult(data.player, data.holeData.holeNumber, strokesNumber)
            val nextPlayer = playerRepository.getNextPlayer(holeInputState.value.player)
            if (nextPlayer != null) {
                _holeInputState.update { it.copy(player = nextPlayer) }
            } else {
                val nextHole = holeRepository.getHoleData(holeInputState.value.holeData.holeNumber + 1)
                if (nextHole != null) {
                    _holeInputState.update {
                        HoleInputState(
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

    private suspend fun setWinner() {
        val player1 = playerRepository.getFirstPlayer()
        val player2 = playerRepository.getNextPlayer(player1) ?: return
        val player1Result = resultsRepository.getResults(1) ?: return
        val player2Result = resultsRepository.getResults(2) ?: return //TODO - обработка ошибок
        val scores =
            ResultsComparator.getScores(mapOf(player1 to player1Result, player2 to player2Result))
        val winner = ResultsComparator.getWinner(scores)
        val result = if (winner == null) {
            GameResultState.Draw(scores[0], scores[1])
        } else {
            GameResultState.Win(winner.player, scores[0], scores[1])
        }

        _gameResultState.update { result }
    }

    private fun onNewGame() {
        runBlocking {
            resultsRepository.clearResults()
            playerRepository.clearPlayers()
            _holeInputState.update {
                HoleInputState(
                    holeData = holeRepository.getFirstHole(),
                    player = playerRepository.getFirstPlayer()
                )
            }
            _gameResultState.update { GameResultState.InProgress }
        }
    }
}