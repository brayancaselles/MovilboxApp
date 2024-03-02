package com.brayandev.movilboxapp.domain.useCase.category

import com.brayandev.movilboxapp.data.repository.CategoryRepository
import javax.inject.Inject

class RequestCategoriesUseCase @Inject constructor(private val repository: CategoryRepository) {

    suspend fun invoke() = repository.requestCategories()
}
