package com.brayandev.movilboxapp.domain.useCase.detail

import com.brayandev.movilboxapp.data.repository.DetailProductRepository
import com.brayandev.movilboxapp.domain.model.ProductModel
import javax.inject.Inject

class UpdateProductUseCase @Inject constructor(private val repository: DetailProductRepository) {

    suspend fun updateProduct(product: ProductModel): Boolean {
        return repository.updateProduct(product)
    }
}
