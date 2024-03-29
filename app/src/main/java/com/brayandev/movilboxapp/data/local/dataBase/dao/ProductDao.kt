package com.brayandev.movilboxapp.data.local.dataBase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.brayandev.movilboxapp.data.local.dataBase.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM product_table WHERE id = :id")
    fun getProduct(id: Int): Flow<ProductEntity>

    @Query("SELECT COUNT(id) FROM product_table")
    suspend fun countProducts(): Int

    @Query("SELECT * FROM product_table ORDER BY rating DESC")
    fun getAllProducts(): Flow<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProduct(productEntity: List<ProductEntity>)

    @Update
    suspend fun updateProduct(productEntity: ProductEntity)

    @Delete
    suspend fun deleteProduct(productEntity: ProductEntity)
}
