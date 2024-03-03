package com.brayandev.movilboxapp.data.repository

import com.brayandev.movilboxapp.data.local.LocalDataSource
import com.brayandev.movilboxapp.data.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
) {

    val categories: Flow<List<String>> = localDataSource.categories

    suspend fun requestCategories() {
        val isDBEmpty = localDataSource.countCategories() == 0

        when (isDBEmpty) {
            true -> {
                localDataSource.insertCategories(
                    remoteDataSource.requestCategoriesFromApi().list,
                )
            }

            false -> {
                val request = remoteDataSource.requestCategoriesFromApi().list
                localDataSource.insertCategories(request)
            }
        }
    }
}
