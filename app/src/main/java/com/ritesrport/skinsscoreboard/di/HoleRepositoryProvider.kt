package com.ritesrport.skinsscoreboard.di

import com.ritesrport.skinsscoreboard.data.HoleRepositoryImpl
import com.ritesrport.skinsscoreboard.domain.repository.HoleRepository

object HoleRepositoryProvider {
    private val repository: HoleRepository by lazy { HoleRepositoryImpl() }

    fun provideRepository(): HoleRepository = repository
}