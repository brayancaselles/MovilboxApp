package com.brayandev.movilboxapp.data.di

import android.content.Context
import androidx.room.Room
import com.brayandev.movilboxapp.data.local.dataBase.DataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): DataBase {
        return Room.databaseBuilder(context, DataBase::class.java, "movilbox_db").build()
    }

    @Provides
    @Singleton
    fun provideProductDao(dataBase: DataBase) = dataBase.productDao()
}
