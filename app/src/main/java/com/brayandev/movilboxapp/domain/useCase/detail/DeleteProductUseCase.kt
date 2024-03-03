package com.brayandev.movilboxapp.domain.useCase.detail

import com.brayandev.movilboxapp.data.repository.DetailProductRepository
import com.brayandev.movilboxapp.domain.model.ProductModel
import javax.inject.Inject

class DeleteProductUseCase @Inject constructor(private val repository: DetailProductRepository) {

    suspend operator fun invoke(product: ProductModel): Boolean = repository.deleteProduct(product)
}
