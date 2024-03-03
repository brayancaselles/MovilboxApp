package com.brayandev.movilboxapp.domain.useCase.category

import com.brayandev.movilboxapp.data.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(private val repository: CategoryRepository) {

    operator fun invoke(): Flow<List<String>> = repository.categories
}
