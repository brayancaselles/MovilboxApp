package com.brayandev.movilboxapp.data.local

import com.brayandev.movilboxapp.data.local.dataBase.dao.ProductDao
import com.brayandev.movilboxapp.data.local.dataBase.entity.toProductModel
import com.brayandev.movilboxapp.domain.model.ProductModel
import com.brayandev.movilboxapp.domain.model.toProductEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val productDao: ProductDao) {

    val products: Flow<List<ProductModel>> =
        productDao.getAllProducts().map { products -> products.map { it.toProductModel() } }

    suspend fun countProducts(): Int = productDao.countProducts()

    suspend fun insertAllProduct(product: List<ProductModel>) =
        productDao.insertAllProduct(product.map { it.toProductEntity() })

    suspend fun updateProduct(product: ProductModel) =
        productDao.updateProduct(product.toProductEntity())

    suspend fun deleteProduct(product: ProductModel) =
        productDao.deleteProduct(product.toProductEntity())
}
