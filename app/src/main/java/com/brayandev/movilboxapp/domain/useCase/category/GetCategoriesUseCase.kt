package com.brayandev.movilboxapp.domain.useCase.category

import com.brayandev.movilboxapp.data.repository.CategoryRepository
import com.brayandev.movilboxapp.domain.model.CategoriesModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(private val repository: CategoryRepository) {

    operator fun invoke(): Flow<CategoriesModel> = repository.categories
}
