package com.brayandev.movilboxapp.data.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductListResponse(
    val products: List<ProductResponse>,
    val total: Int,
    val skip: Int,
    val limit: Int,
) : Parcelable
