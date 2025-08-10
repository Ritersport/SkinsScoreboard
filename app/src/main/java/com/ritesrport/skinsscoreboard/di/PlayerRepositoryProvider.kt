package com.ritesrport.skinsscoreboard.di

import com.ritesrport.skinsscoreboard.data.PlayerRepositoryImpl
import com.ritesrport.skinsscoreboard.domain.repository.PlayerRepository

object PlayerRepositoryProvider {
    private val repository: PlayerRepository by lazy { PlayerRepositoryImpl() }
    fun provideRepository(): PlayerRepository = repository
}