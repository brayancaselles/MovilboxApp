package com.brayandev.movilboxapp.domain.useCase.detail

import com.brayandev.movilboxapp.data.repository.DetailProductRepository
import com.brayandev.movilboxapp.domain.model.ProductModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDetailProductUseCase @Inject constructor(private val repository: DetailProductRepository) {

    operator fun invoke(productId: Int): Flow<ProductModel> = repository.product(productId)
}
