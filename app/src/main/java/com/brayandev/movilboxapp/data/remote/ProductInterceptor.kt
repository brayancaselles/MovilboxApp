package com.brayandev.movilboxapp.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ProductInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request =
            chain.request().newBuilder().addHeader("Content-type", "application/json").build()
        return chain.proceed(request)
    }
}
