package com.brayandev.movilboxapp.ui.views.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brayandev.movilboxapp.domain.useCase.category.GetCategoriesUseCase
import com.brayandev.movilboxapp.domain.useCase.category.RequestCategoriesUseCase
import com.brayandev.movilboxapp.domain.useCase.product.GetProductUseCase
import com.brayandev.movilboxapp.domain.useCase.product.RequestProductUseCase
import com.brayandev.movilboxapp.ui.views.products.uiState.CategoriesUiState
import com.brayandev.movilboxapp.ui.views.products.uiState.ProductUiState
import com.brayandev.movilboxapp.ui.views.products.uiState.ProductUiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    getProductUseCase: GetProductUseCase,
    private val requestProductUseCase: RequestProductUseCase,
    getCategoriesUseCase: GetCategoriesUseCase,
    private val requestCategoriesUseCase: RequestCategoriesUseCase,
) : ViewModel() {

    val uiState: StateFlow<ProductUiState> = getProductUseCase().map(::Success)
        .catch { ProductUiState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProductUiState.Loading)

    private var _uiStateCategories = MutableStateFlow(CategoriesUiState())
    val uiStateCategories: StateFlow<CategoriesUiState> = _uiStateCategories

    init {
        viewModelScope.launch {
            try {
                async { requestProductUseCase.invoke() }.await()
                async { requestCategoriesUseCase.invoke() }.await()
                async {
                    getCategoriesUseCase.invoke().collect { listCategories ->
                        _uiStateCategories.value = CategoriesUiState(list = listCategories)
                    }
                }.await()
            } catch (e: Exception) {
                ProductUiState.Error(e)
            }
        }
    }
}
