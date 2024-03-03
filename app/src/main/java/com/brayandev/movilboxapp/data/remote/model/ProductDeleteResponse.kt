package com.brayandev.movilboxapp.data.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductDeleteResponse(
    val brand: String,
    val category: String,
    val deletedOn: String,
    val description: String,
    val discountPercentage: Double,
    val id: Int,
    val images: List<String>,
    val isDeleted: Boolean,
    val price: Int,
    val rating: Double,
    val stock: Int,
    val thumbnail: String,
    val title: String,
) : Parcelable