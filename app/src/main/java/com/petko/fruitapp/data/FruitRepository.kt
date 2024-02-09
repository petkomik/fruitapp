package com.petko.fruitapp.data

import com.petko.fruitapp.utils.Fruit
import com.petko.fruitapp.network.FruitApiService

interface FruitRepository {
    suspend fun getFruits(): List<Fruit>
}

class NetworkFruitRepository(
    private val fruitApiService: FruitApiService
) : FruitRepository {
    override suspend fun getFruits(): List<Fruit> = fruitApiService.getFruits()
}