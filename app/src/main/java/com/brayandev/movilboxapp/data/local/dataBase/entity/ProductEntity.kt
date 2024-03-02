package com.brayandev.movilboxapp.data.local.dataBase.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.brayandev.movilboxapp.domain.model.ProductModel

@Entity(tableName = "product_table")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "price") val price: Double,
    @ColumnInfo(name = "discountPercentage") val discountPercentage: Double,
    @ColumnInfo(name = "rating") val rating: Double,
    @ColumnInfo(name = "stock") val stock: Int,
    @ColumnInfo(name = "brand") val brand: String,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "thumbnail") val thumbnail: String,
    @ColumnInfo(name = "images") val images: List<String>,
)

fun ProductEntity.productEntityToProductModel() = ProductModel(
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
