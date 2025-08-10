package com.ritesrport.skinsscoreboard.di

import com.ritesrport.skinsscoreboard.data.ResultsRepositoryImpl
import com.ritesrport.skinsscoreboard.domain.repository.ResultsRepository

object ResultsRepositoryProvider {
    private val repository: ResultsRepository by lazy { ResultsRepositoryImpl() }
    fun provideRepository(): ResultsRepository = repository
}