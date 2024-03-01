package com.brayandev.movilboxapp.data.local.dataBase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.brayandev.movilboxapp.data.local.dataBase.entity.CategoryEntity

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<CategoryEntity>)

    @Query("SELECT * FROM Categories_table")
    suspend fun getCategories(): List<CategoryEntity>

    @Query("SELECT COUNT(id) FROM Categories_table")
    suspend fun categoriesCount(): Int
}
