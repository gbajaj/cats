package com.datalayer.demo.datalayer.cats.di

import com.datalayer.demo.datalayer.cats.impl.CatService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class CatNetworkModule {
    @Provides
    fun provideCatService(): CatService {
        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

        val okHttpClient = OkHttpClient.Builder()
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://cataas.com/") // Replace with your base URL
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))

            .build()

        return retrofit.create(CatService::class.java)
    }
}
