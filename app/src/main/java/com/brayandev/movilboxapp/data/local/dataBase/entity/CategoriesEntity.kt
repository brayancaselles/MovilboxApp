package com.brayandev.movilboxapp.data.local.dataBase.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.brayandev.movilboxapp.domain.model.CategoriesModel

@Entity(tableName = "categories_table")
data class CategoriesEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "categories") val categories: List<String>,
)

fun CategoriesEntity.entityToModel() = CategoriesModel(id = id, categories = categories)
