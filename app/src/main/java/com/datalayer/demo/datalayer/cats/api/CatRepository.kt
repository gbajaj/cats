package com.datalayer.demo.datalayer.cats

import com.datalayer.demo.datalayer.cats.api.Cat
import kotlinx.coroutines.flow.Flow

interface CatRepository {
    fun getCats(): Flow<List<Cat>>
}