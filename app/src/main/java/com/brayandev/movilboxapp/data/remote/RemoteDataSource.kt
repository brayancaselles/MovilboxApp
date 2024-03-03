package com.brayandev.movilboxapp.data.remote

import android.util.Log
import com.brayandev.movilboxapp.data.remote.model.CategoriesResponse
import com.brayandev.movilboxapp.data.remote.model.ProductResponse
import com.brayandev.movilboxapp.data.remote.network.ApiService
import com.brayandev.movilboxapp.domain.model.ProductModel
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun requestProductsFromApi(): List<ProductResponse> {
        var listResponse: List<ProductResponse> = emptyList()
        runCatching { apiService.getProducts() }
            .onSuccess {
                if (it.isSuccessful && it.body() != null) listResponse = it.body()!!.products
            }.onFailure {
                Log.d("RemoteDataSource", "Error getting products: ${it.message}")
            }

        return listResponse
    }

    suspend fun requestCategoriesFromApi(): CategoriesResponse {
        var listResponse: List<String> = emptyList()
        runCatching { apiService.getCategories() }
            .onSuccess {
                if (it.isSuccessful && it.body() != null) listResponse = it.body()!!
            }
            .onFailure {
                Log.d("RemoteDataSource", "Error getting products: ${it.message}")
            }
        return CategoriesResponse(listResponse)
    }

    suspend fun deleteProduct(productId: Int): Boolean {
        runCatching { apiService.deleteProduct(productId) }.onSuccess {
            if (it.isSuccessful && it.body() != null) {
                return it.body()!!.isDeleted
            }
        }.onFailure {
            Log.d("RemoteDataSource", "Error deleting product: ${it.message}")
        }
        return false
    }

    suspend fun updateProduct(productModel: ProductModel): Boolean {
        runCatching { apiService.updateProduct(productModel.id, productModel) }.onSuccess {
            return true
        }.onFailure {
            Log.d("RemoteDataSource", "Error deleting product: ${it.message}")
        }
        return false
    }
}
