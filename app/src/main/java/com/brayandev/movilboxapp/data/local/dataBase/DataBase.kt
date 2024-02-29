package com.brayandev.movilboxapp.data.local.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.brayandev.movilboxapp.data.local.dataBase.converter.Converter
import com.brayandev.movilboxapp.data.local.dataBase.dao.ProductDao
import com.brayandev.movilboxapp.data.local.dataBase.entity.ProductEntity

@Database(entities = [ProductEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class DataBase : RoomDatabase() {

    abstract fun productDao(): ProductDao
}
