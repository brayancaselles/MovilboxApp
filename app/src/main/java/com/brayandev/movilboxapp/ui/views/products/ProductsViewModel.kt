package com.brayandev.movilboxapp.ui.views.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brayandev.movilboxapp.domain.useCase.GetProductUseCase
import com.brayandev.movilboxapp.domain.useCase.RequestProductUseCase
import com.brayandev.movilboxapp.ui.views.products.ProductUiState.Error
import com.brayandev.movilboxapp.ui.views.products.ProductUiState.Loading
import com.brayandev.movilboxapp.ui.views.products.ProductUiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
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
    requestProductUseCase: RequestProductUseCase,
) : ViewModel() {

    val uiState: StateFlow<ProductUiState> = getProductUseCase().map(::Success)
        .catch { Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)

    init {
        viewModelScope.launch {
            requestProductUseCase.invoke()
        }
    }
}
