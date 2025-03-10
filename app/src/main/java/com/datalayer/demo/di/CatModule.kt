package com.datalayer.demo.di

import com.datalayer.demo.datalayer.cats.CatRepository
import com.datalayer.demo.datalayer.cats.impl.CatRepositoryImpl
import com.datalayer.demo.datalayer.cats.impl.CatService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class CatModule {
    @Provides
    fun provideCatRepository(catService: CatService): CatRepository {
        return CatRepositoryImpl(catService)
    }
}