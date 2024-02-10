package com.petko.fruitapp.data

import kotlinx.coroutines.flow.Flow
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FruitDao {
    @Query("SELECT * from fruits ORDER BY name ASC")
    fun getAllItems(): Flow<List<FruitDB>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: FruitDB)

    @Delete
    suspend fun delete(item: FruitDB)
}