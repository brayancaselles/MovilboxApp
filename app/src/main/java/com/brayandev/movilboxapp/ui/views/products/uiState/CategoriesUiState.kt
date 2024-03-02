package com.brayandev.movilboxapp.ui.views.products.uiState

import com.brayandev.movilboxapp.domain.model.CategoriesModel

sealed interface CategoriesUiState {
    object Loading : CategoriesUiState
    data class Error(val throwable: Throwable) : CategoriesUiState
    data class SuccessCategories(val list: CategoriesModel) : CategoriesUiState
}
