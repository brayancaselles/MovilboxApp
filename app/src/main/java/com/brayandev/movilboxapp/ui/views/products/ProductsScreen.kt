package com.brayandev.movilboxapp.ui.views.products

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import com.brayandev.movilboxapp.R
import com.brayandev.movilboxapp.ui.views.products.ProductUiState.Error
import com.brayandev.movilboxapp.ui.views.products.ProductUiState.Loading
import com.brayandev.movilboxapp.ui.views.products.ProductUiState.Success

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(productsViewModel: ProductsViewModel, navController: NavHostController) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val uiState by produceState<ProductUiState>(
        initialValue = Loading,
        key1 = lifecycle,
        key2 = productsViewModel,
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            productsViewModel.uiState.collect { value = it }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = stringResource(R.string.text_title_top_app_bar),
                            textAlign = TextAlign.Center,
                        )
                    }
                },
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
            ) {
                /*ComponentSearchBar(searchText = searchText, onSearchTextChange = { newText ->
                    searchText = newText
                    currentList = applyFilterAndOrder(originalList, searchText, orderBy)
                })

                if (listCategories.isNotEmpty()) {
                    var itemSelected by remember { mutableStateOf(listCategories[0]) }

                    ComponentCategoriesList(
                        listCategories,
                        onItemSelected = { itemSelected = it },
                    )

                    currentList =
                        applyFilterAndOrder(originalList, searchText, orderBy, itemSelected)
                } else {
                    currentList = applyFilterAndOrder(originalList, searchText, orderBy)
                }

                ComponentProductsColumn(currentList, navController)*/
                when (uiState) {
                    is Error -> {
                    }

                    Loading -> {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is Success -> {
                    }
                }
            }
        },
    )
}
