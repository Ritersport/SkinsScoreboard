package com.ritesrport.skinsscoreboard.data

import com.ritesrport.skinsscoreboard.domain.GameConstants.HOLE_PARS
import com.ritesrport.skinsscoreboard.domain.entity.HoleData
import com.ritesrport.skinsscoreboard.domain.repository.HoleRepository

class HoleRepositoryImpl : HoleRepository {

    override suspend fun getHoleData(holeNumber: Int): HoleData? {
        if (holeNumber > HOLE_PARS.size) {
            return null
        }
        return HoleData(holeNumber, HOLE_PARS[holeNumber - 1])
    }

    override suspend fun getFirstHole(): HoleData {
        return HoleData(1, HOLE_PARS[0])
    }
}