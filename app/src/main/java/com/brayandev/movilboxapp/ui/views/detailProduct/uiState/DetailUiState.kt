package com.brayandev.movilboxapp.ui.views.detailProduct.uiState

import com.brayandev.movilboxapp.domain.model.ProductModel

sealed interface DetailUiState {
    object Loading : DetailUiState
    data class Error(val throwable: Throwable) : DetailUiState
    data class Success(val product: ProductModel) : DetailUiState
}
