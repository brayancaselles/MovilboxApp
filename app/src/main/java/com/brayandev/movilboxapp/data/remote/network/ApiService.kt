package com.brayandev.movilboxapp.data.remote.network

import com.brayandev.movilboxapp.data.remote.model.ProductListResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("products/")
    suspend fun getProducts(): Response<ProductListResponse>

    @GET("products/categories")
    suspend fun getCategories(): Response<List<String>>
}
