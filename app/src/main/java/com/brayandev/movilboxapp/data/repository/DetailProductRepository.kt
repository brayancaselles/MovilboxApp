package com.brayandev.movilboxapp.data.repository

import com.brayandev.movilboxapp.data.local.LocalDataSource
import com.brayandev.movilboxapp.data.remote.RemoteDataSource
import com.brayandev.movilboxapp.domain.model.ProductModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DetailProductRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
) {

    fun product(productId: Int): Flow<ProductModel> = localDataSource.product(productId)

    suspend fun deleteProduct(product: ProductModel): Boolean {
        val request = remoteDataSource.deleteProduct(product.id)
        if (request) {
            localDataSource.deleteProduct(product)
        }
        return request
    }

    suspend fun updateProduct(product: ProductModel): Boolean {
        val request = remoteDataSource.updateProduct(product)
        if (request) {
            localDataSource.updateProduct(product)
        }
        return request
    }
}
