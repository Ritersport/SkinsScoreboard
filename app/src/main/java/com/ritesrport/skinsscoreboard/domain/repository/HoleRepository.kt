package com.ritesrport.skinsscoreboard.domain.repository

import com.ritesrport.skinsscoreboard.domain.entity.HoleData

interface HoleRepository {
    suspend fun getHoleData(holeNumber: Int): HoleData?
    suspend fun getFirstHole(): HoleData
}