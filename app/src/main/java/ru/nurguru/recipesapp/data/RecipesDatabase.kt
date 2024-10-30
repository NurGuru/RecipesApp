package ru.nurguru.recipesapp.data

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.nurguru.recipesapp.model.Category

@Database(entities = [Category::class], version = 1)
abstract class RecipesDatabase : RoomDatabase() {
    abstract fun categoriesDao(): CategoriesDao
}
