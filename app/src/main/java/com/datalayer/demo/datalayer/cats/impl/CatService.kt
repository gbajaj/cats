package com.datalayer.demo.datalayer.cats.impl

import com.datalayer.demo.datalayer.cats.api.Cat
import retrofit2.Response
import retrofit2.http.GET

interface CatService {
    @GET("api/cats") // Replace with your actual endpoint
    suspend fun getCats(): Response<List<Cat>>
}