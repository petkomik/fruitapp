package com.petko.fruitapp.network

import com.petko.fruitapp.utils.Fruit
import retrofit2.http.GET

interface FruitApiService {
    @GET("fruit/all")
    suspend fun getFruits(): List<Fruit>
}