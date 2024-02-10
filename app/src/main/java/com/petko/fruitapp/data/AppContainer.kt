package com.petko.fruitapp.data

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.petko.fruitapp.network.FruitApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val networkFruitRepository: FruitRepository
    val offlineFruitRepository: FruitRepository
}

class DefaultAppContainer (private val context: Context) : AppContainer {
    private val baseUrl = "https://www.fruityvice.com/api/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: FruitApiService by lazy {
        retrofit.create(FruitApiService::class.java)
    }

    override val networkFruitRepository: FruitRepository by lazy {
        NetworkFruitRepository(retrofitService)
    }

    override val offlineFruitRepository: FruitRepository by lazy {
        OfflineFruitsRepository(FruitsDatabase.getDatabase(context).fruitDao())
    }
}