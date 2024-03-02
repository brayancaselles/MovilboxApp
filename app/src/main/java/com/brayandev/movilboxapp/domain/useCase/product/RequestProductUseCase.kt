package com.brayandev.movilboxapp.domain.useCase.product

import com.brayandev.movilboxapp.data.repository.ProductRepository
import javax.inject.Inject

class RequestProductUseCase @Inject constructor(private val repository: ProductRepository) {

    suspend operator fun invoke() = repository.requestProducts()
}
