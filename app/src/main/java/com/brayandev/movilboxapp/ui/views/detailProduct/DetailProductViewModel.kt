package com.brayandev.movilboxapp.ui.views.detailProduct

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brayandev.movilboxapp.domain.model.ProductModel
import com.brayandev.movilboxapp.domain.useCase.detail.DeleteProductUseCase
import com.brayandev.movilboxapp.domain.useCase.detail.GetDetailProductUseCase
import com.brayandev.movilboxapp.domain.useCase.detail.UpdateProductUseCase
import com.brayandev.movilboxapp.ui.views.detailProduct.uiState.DetailUiState
import com.brayandev.movilboxapp.ui.views.detailProduct.uiState.DetailUiState.Error
import com.brayandev.movilboxapp.ui.views.detailProduct.uiState.DetailUiState.Loading
import com.brayandev.movilboxapp.ui.views.detailProduct.uiState.DetailUiState.Success
import com.brayandev.movilboxapp.ui.views.navigation.PRODUCT_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class DetailProductViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getProductUseCase: GetDetailProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
) : ViewModel() {

    private var productId by Delegates.notNull<Int>()

    init {
        productId = savedStateHandle[PRODUCT_ID] ?: 0
    }

    val uiState: StateFlow<DetailUiState> =
        getProductUseCase(productId).map(::Success).catch { Error(it) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)
    private var _isDeleted = MutableLiveData<Boolean>()

    val isDeleted: LiveData<Boolean> = _isDeleted

    fun deleteProduct(product: ProductModel) {
        viewModelScope.launch {
            _isDeleted.value = deleteProductUseCase(product)
        }
    }
}
