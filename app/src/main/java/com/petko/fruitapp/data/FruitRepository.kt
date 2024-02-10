package com.petko.fruitapp.data

import com.petko.fruitapp.utils.Fruit
import com.petko.fruitapp.network.FruitApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

interface FruitRepository {
    suspend fun getFruits(): List<Fruit>
    fun getAllItemsStream(): Flow<List<FruitDB>>
    suspend fun insertItem(item: FruitDB)

    suspend fun deleteItem(item: FruitDB)

}

class NetworkFruitRepository(
    private val fruitApiService: FruitApiService
) : FruitRepository {
    override suspend fun getFruits(): List<Fruit> = fruitApiService.getFruits()
    override fun getAllItemsStream(): Flow<List<FruitDB>> {return emptyFlow()}
    override suspend fun insertItem(item: FruitDB) {}
    override suspend fun deleteItem(item: FruitDB) {}
}

class OfflineFruitsRepository(private val fruitDao: FruitDao) : FruitRepository {
    override suspend fun getFruits(): List<Fruit> {return emptyList<Fruit>()}
    override fun getAllItemsStream(): Flow<List<FruitDB>> = fruitDao.getAllItems()
    override suspend fun insertItem(item: FruitDB) = fruitDao.insert(item)
    override suspend fun deleteItem(item: FruitDB) = fruitDao.delete(item)

}