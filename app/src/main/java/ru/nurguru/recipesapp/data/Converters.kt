package ru.nurguru.recipesapp.data

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.nurguru.recipesapp.model.Ingredient

class Converters {
    @TypeConverter
    fun fromIngredientsList(ingredients: List<Ingredient>?): String {
        return Json.encodeToString(ingredients)
    }

    @TypeConverter
    fun toIngredientList(ingredientsString: String?): List<Ingredient> {
        return Json.decodeFromString(ingredientsString ?: "")
    }

    @TypeConverter
    fun fromMethodsList(method: List<String>?): String {
        return Json.encodeToString(method)
    }

    @TypeConverter
    fun toMethodsList(methodString: String?): List<String> {
        return Json.decodeFromString(methodString ?: "")
    }
}