package ru.nurguru.recipesapp

import android.app.Application
import ru.nurguru.recipesapp.di.AppContainer

class RecipesApplication: Application() {
    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}