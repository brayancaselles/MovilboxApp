package com.brayandev.movilboxapp.data.local

import com.brayandev.movilboxapp.data.local.dataBase.dao.CategoryDao
import com.brayandev.movilboxapp.data.local.dataBase.dao.ProductDao
import com.brayandev.movilboxapp.data.local.dataBase.entity.CategoriesEntity
import com.brayandev.movilboxapp.data.local.dataBase.entity.productEntityToProductModel
import com.brayandev.movilboxapp.domain.model.ProductModel
import com.brayandev.movilboxapp.domain.model.productModelToProductEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val productDao: ProductDao,
    private val categoryDao: CategoryDao,
) {

    val products: Flow<List<ProductModel>> =
        productDao.getAllProducts()
            .map { products -> products.map { it.productEntityToProductModel() } }

    val categories: Flow<List<String>> = categoryDao.getCategories().map { it.categories }

    fun product(productId: Int): Flow<ProductModel> =
        productDao.getProduct(productId).map { item -> item.productEntityToProductModel() }

    suspend fun countProducts(): Int = productDao.countProducts()

    suspend fun countCategories(): Int = categoryDao.categoriesCount()

    suspend fun insertAllProduct(product: List<ProductModel>) =
        productDao.insertAllProduct(product.map { it.productModelToProductEntity() })

    suspend fun insertCategories(categories: List<String>) =
        categoryDao.insertCategories(CategoriesEntity(categories = categories))

    suspend fun updateProduct(product: ProductModel) =
        productDao.updateProduct(product.productModelToProductEntity())

    suspend fun deleteProduct(product: ProductModel) =
        productDao.deleteProduct(product.productModelToProductEntity())
}
