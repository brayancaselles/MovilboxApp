package com.brayandev.movilboxapp.data.remote.network

import com.brayandev.movilboxapp.data.remote.model.ProductDeleteResponse
import com.brayandev.movilboxapp.data.remote.model.ProductListResponse
import com.brayandev.movilboxapp.domain.model.ProductModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @GET("products/")
    suspend fun getProducts(): Response<ProductListResponse>

    @GET("products/categories")
    suspend fun getCategories(): Response<List<String>>

    @DELETE("products/{id}")
    suspend fun deleteProduct(@Path("id") id: Int): Response<ProductDeleteResponse>

    @PUT("products/{id}")
    suspend fun updateProduct(@Path("id") id: Int, @Body product: ProductModel)
}
