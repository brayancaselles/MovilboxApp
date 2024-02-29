package com.brayandev.movilboxapp.data.dataBase.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

interface ProductDao {

    /*@Query("SELECT * FROM product_table WHERE id = :productId")
    fun getAllProducts(productId: Int): Flow<List<>>*/

    @Insert
    suspend fun insertProduct()

    @Update
    suspend fun updateProduct()

    @Delete
    suspend fun deleteProduct()
}
