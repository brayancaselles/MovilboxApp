package com.brayandev.movilboxapp.data.local.dataBase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.brayandev.movilboxapp.data.local.dataBase.entity.CategoriesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert()
    suspend fun insertCategories(categories: CategoriesEntity)

    @Query("SELECT * FROM Categories_table ")
    fun getCategories(): Flow<CategoriesEntity>

    @Query("SELECT COUNT(id) FROM Categories_table")
    suspend fun categoriesCount(): Int
}
