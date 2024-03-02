package com.brayandev.movilboxapp.ui.views.products.uiState

import com.brayandev.movilboxapp.domain.model.ProductModel

sealed interface ProductUiState {
    object Loading : ProductUiState
    data class Error(val throwable: Throwable) : ProductUiState
    data class Success(val list: List<ProductModel>) : ProductUiState
}
