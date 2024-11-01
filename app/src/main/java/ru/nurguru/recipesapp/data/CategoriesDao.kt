package ru.nurguru.recipesapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.nurguru.recipesapp.model.Category
import ru.nurguru.recipesapp.model.Constants.CATEGORY

@Dao
interface CategoriesDao {
    @Query("SELECT * FROM $CATEGORY")
    fun getCategories(): List<Category>

    //    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>
//
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
//
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCategories(categories: List<Category>)
//
//    @Delete
//    fun delete(user: User)
}