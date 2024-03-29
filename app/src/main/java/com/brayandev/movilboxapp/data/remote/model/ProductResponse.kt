package com.brayandev.movilboxapp.data.remote.model

import android.os.Parcelable
import com.brayandev.movilboxapp.domain.model.ProductModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductResponse(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val brand: String,
    val category: String,
    val thumbnail: String,
    val images: List<String>,
) : Parcelable

fun ProductResponse.toProductDomain() = ProductModel(
    id = id,
    title = title,
    description = description,
    price = price,
    discountPercentage = discountPercentage,
    rating = rating,
    stock = stock,
    brand = brand,
    category = category,
    thumbnail = thumbnail,
    images = images,
)
