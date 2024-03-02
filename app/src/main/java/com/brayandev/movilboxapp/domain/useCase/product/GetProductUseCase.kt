package com.brayandev.movilboxapp.domain.useCase.product

import com.brayandev.movilboxapp.data.repository.ProductRepository
import com.brayandev.movilboxapp.domain.model.ProductModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductUseCase @Inject constructor(private val repository: ProductRepository) {

    operator fun invoke(): Flow<List<ProductModel>> = repository.products
}
