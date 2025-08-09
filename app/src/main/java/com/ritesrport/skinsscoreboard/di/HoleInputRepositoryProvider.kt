package com.ritesrport.skinsscoreboard.di

import com.ritesrport.skinsscoreboard.data.HoleInputRepositoryImpl
import com.ritesrport.skinsscoreboard.domain.HoleInputRepository

object HoleInputRepositoryProvider {
    private val repository: HoleInputRepository by lazy { HoleInputRepositoryImpl() }

    fun provideRepository(): HoleInputRepository = repository
}