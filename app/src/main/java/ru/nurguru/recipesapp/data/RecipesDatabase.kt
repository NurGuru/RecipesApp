package ru.nurguru.recipesapp.data

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.nurguru.recipesapp.model.Category
import ru.nurguru.recipesapp.model.Recipe

@Database(entities = [Category::class, Recipe::class], version = 1)
@TypeConverters(Converters::class)
abstract class RecipesDatabase : RoomDatabase() {
    abstract fun categoriesDao(): CategoriesDao
    abstract fun recipesDao(): RecipesDao
}
