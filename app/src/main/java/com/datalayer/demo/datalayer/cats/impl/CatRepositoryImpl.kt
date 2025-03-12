package com.datalayer.demo.datalayer.cats.impl

import com.datalayer.demo.datalayer.cats.CatRepository
import com.datalayer.demo.datalayer.cats.api.Cat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class CatRepositoryImpl
@Inject
    constructor(private val catService: CatService):CatRepository {
    override fun getCats(): Flow<List<Cat>> = flow {
        val response = catService.getCats()
        if (response.isSuccessful) {
            emit(response.body()?.map {
                val ret = response.raw().request.url.let {
                    it.scheme  + "://" + it.host
                }
                it.copy(downloadUrl = ret +"/cat/"+ it.id)
            } ?: emptyList())
        } else {
            emit(emptyList())
        }
    }.flowOn(Dispatchers.IO)
}