package com.brayandev.movilboxapp.ui.views.products

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.brayandev.movilboxapp.R
import com.brayandev.movilboxapp.domain.model.ProductModel
import com.brayandev.movilboxapp.ui.theme.BackgroundItem
import com.brayandev.movilboxapp.ui.theme.TextColor
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
                            fontWeight = FontWeight.Bold,
                            color = TextColor,
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
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is Success -> {
                        ProductScreen(
                            list = (uiState as Success).list,
                            navController = navController,
                        )
                    }
                }
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    list: List<ProductModel>,
    navController: NavHostController,
) {
    var filterText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue(),
        )
    }

    val filteredItems = rememberSaveable(filterText) {
        list.filter {
            it.title.contains(filterText.text, ignoreCase = true)
        }
    }

    // var expanded by rememberSaveable { mutableStateOf(false) }

    val sheetStateCategory = rememberModalBottomSheetState()
    var isSheetOpenCategory by rememberSaveable { mutableStateOf(false) }

    val sheetStateFilter = rememberModalBottomSheetState()
    var isSheetOpenFilter by rememberSaveable { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            OutlinedTextField(
                value = filterText,
                onValueChange = { filterText = it },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "Icon_store_dialog",
                        modifier = Modifier.size(18.dp),
                        tint = TextColor,
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.text_search_product),
                        color = TextColor,
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.text_name_product),
                        color = TextColor,
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = BackgroundItem,
                    unfocusedContainerColor = BackgroundItem,
                    disabledContainerColor = BackgroundItem,
                    focusedBorderColor = TextColor,
                    unfocusedBorderColor = TextColor,
                ),
            )
            Button(
                onClick = { isSheetOpenFilter = true },
                colors = ButtonDefaults.buttonColors(containerColor = BackgroundItem),
                shape = RoundedCornerShape(4.dp),
            ) {
                Icon(
                    imageVector = Icons.Rounded.FilterList,
                    contentDescription = "icon_filter",
                    tint = TextColor,
                )
            }
            if (isSheetOpenFilter) {
                MyModalBottomSheet(
                    sheetState = sheetStateFilter,
                    isDismissed = { isSheetOpenFilter = it },
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                onClick = {
                    isSheetOpenCategory = true
                },
                colors = ButtonDefaults.buttonColors(containerColor = BackgroundItem),
                shape = RoundedCornerShape(4.dp),
            ) {
                Icon(
                    imageVector = Icons.Rounded.Category,
                    contentDescription = "icon_filter",
                    tint = TextColor,
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = stringResource(R.string.text_category),
                    color = TextColor,
                )
            }
        }
        if (isSheetOpenCategory) {
            MyModalBottomSheet(
                sheetState = sheetStateCategory,
                isDismissed = { isSheetOpenCategory = it },
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Adaptive(180.dp),
            modifier = Modifier.padding(16.dp),
            contentPadding = PaddingValues(2.dp),
        ) {
            items(filteredItems) { item ->
                ProductItemScreen(item, navController)
            }
        }
    }
}

@Composable
fun ProductItemScreen(product: ProductModel, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { },
        colors = CardDefaults.cardColors(containerColor = BackgroundItem),
    ) {
        Column(verticalArrangement = Arrangement.SpaceBetween) {
            Box(/*modifier = Modifier.padding(bottom = 4.dp)*/) {
                AsyncImage(
                    model = product.images[0],
                    contentDescription = product.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(2 / 2f),
                    alignment = Alignment.TopCenter,
                )
            }
            Text(
                text = product.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "$ ${product.price}",
                modifier = Modifier.padding(start = 8.dp, bottom = 2.dp),
                color = TextColor,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyModalBottomSheet(
    list: List<String> = listOf(),
    sheetState: SheetState,
    isDismissed: (Boolean) -> Unit,
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            isDismissed(false)
        },
        containerColor = BackgroundItem,
    ) {
        Icon(
            imageVector = Icons.Rounded.Category,
            contentDescription = "icon_filter",
            tint = TextColor,
        )
    }
}
