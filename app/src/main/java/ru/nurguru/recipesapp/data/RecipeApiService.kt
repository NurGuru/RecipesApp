package ru.nurguru.recipesapp.data




import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.nurguru.recipesapp.model.Category
import ru.nurguru.recipesapp.model.Constants.CATEGORY
import ru.nurguru.recipesapp.model.Constants.RECIPE
import ru.nurguru.recipesapp.model.Constants.RECIPES
import ru.nurguru.recipesapp.model.Recipe


interface RecipeApiService {
    @GET("$RECIPE/{id}")
    fun getRecipeById(@Path("id") recipeId: Int): Call<Recipe>

    @GET(RECIPES)
    fun getRecipesByIds(@Query("ids") recipesIds: String): Call<List<Recipe>>

    @GET("$CATEGORY/{id}")
    fun getCategoryById(@Path("id") categoryId: Int): Call<Category>

    @GET("$CATEGORY/{id}/$RECIPES")
    fun getRecipesByCategoryId(@Path("id") categoryId: Int): Call<List<Recipe>>

    @GET(CATEGORY)
    fun getCategories(): Call<List<Category>>
}