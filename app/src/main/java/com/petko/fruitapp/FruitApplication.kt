package com.petko.fruitapp

import android.app.Application
import com.petko.fruitapp.data.AppContainer
import com.petko.fruitapp.data.DefaultAppContainer

class FruitApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
