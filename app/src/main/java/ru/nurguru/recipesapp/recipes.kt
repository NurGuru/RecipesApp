package ru.nurguru.recipesapp

import ru.nurguru.recipesapp.models.Categories

object STUB {
   private val categoriesList = listOf<Categories>(
        Categories(
            0,
            "Бургеры",
            "Рецепты всех популярных видов бургеров",
            "burger.png"
        ),
        Categories(
            1,
            "Десерты",
            "Самые вкусные рецепты десертов специально для вас",
            "dessert.png"
        )
    )

}