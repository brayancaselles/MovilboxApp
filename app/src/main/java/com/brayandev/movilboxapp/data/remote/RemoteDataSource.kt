package com.brayandev.movilboxapp.data.remote

import android.util.Log
import com.brayandev.movilboxapp.data.remote.model.CategoriesResponse
import com.brayandev.movilboxapp.data.remote.model.ProductResponse
import com.brayandev.movilboxapp.data.remote.network.ApiService
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
}
