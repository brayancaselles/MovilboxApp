package com.brayandev.movilboxapp.data.repository

import com.brayandev.movilboxapp.data.local.LocalDataSource
import com.brayandev.movilboxapp.data.remote.RemoteDataSource
import com.brayandev.movilboxapp.data.remote.model.toProductDomain
import com.brayandev.movilboxapp.domain.model.ProductModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
) {

    val products: Flow<List<ProductModel>> = localDataSource.products

    suspend fun requestProducts() {
        val isDBEmpty = localDataSource.countProducts() == 0
        if (isDBEmpty) {
            localDataSource.insertAllProduct(
                remoteDataSource.requestProductsFromApi().map { it.toProductDomain() },
            )
        }
    }
}
