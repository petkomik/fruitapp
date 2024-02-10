package com.petko.fruitapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FruitDB::class], version = 1, exportSchema = false)
abstract class FruitsDatabase : RoomDatabase() {

    abstract fun fruitDao(): FruitDao

    companion object {
        @Volatile
        private var Instance: FruitsDatabase? = null

        fun getDatabase(context: Context): FruitsDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, FruitsDatabase::class.java, "fruit_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}