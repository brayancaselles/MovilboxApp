package com.brayandev.movilboxapp.ui.views.products

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import com.brayandev.movilboxapp.R
import com.brayandev.movilboxapp.domain.model.ProductModel
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
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
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
                        ProductScreen(list = (uiState as Success).list)
                    }
                }
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(list: List<ProductModel>) {
    var filterText by rememberSaveable { mutableStateOf(TextFieldValue()) }

    val filteredItems = remember(filterText) {
        list.filter {
            it.title.contains(filterText.text, ignoreCase = true)
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            OutlinedTextField(
                value = filterText,
                onValueChange = { filterText = it },
                modifier = Modifier
                    .padding(16.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "Icon_store_dialog",
                        modifier = Modifier.size(18.dp),
                        tint = LocalContentColor.
                    )
                },
                label = { Text(text = stringResource(id = R.string.text_search_product)) },
                placeholder = {
                    Text(text = stringResource(id = R.string.text_name_product))
                },
                singleLine = true,
            )
            Icon(
                modifier = Modifier
                    .padding(16.dp),
                imageVector = Icons.Rounded.FilterList,
                contentDescription = "icon_filter",
            )
        }
        LazyColumn {
            items(filteredItems, key = { it.title }) { item ->
                ProductItemScreen(item)
            }
        }
    }
}

@Composable
fun ProductItemScreen(product: ProductModel) {
}
