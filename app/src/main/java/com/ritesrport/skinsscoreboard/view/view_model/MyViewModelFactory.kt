package com.ritesrport.skinsscoreboard.view.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ritesrport.skinsscoreboard.domain.repository.HoleRepository
import com.ritesrport.skinsscoreboard.domain.repository.PlayerRepository
import com.ritesrport.skinsscoreboard.domain.repository.ResultsRepository

class MyViewModelFactory(
    private val holeRepository: HoleRepository,
    private val playerRepository: PlayerRepository,
    private val resultsRepository: ResultsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(holeRepository, playerRepository, resultsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}