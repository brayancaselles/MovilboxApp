package com.brayandev.movilboxapp.ui.views.products

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.brayandev.movilboxapp.R
import com.brayandev.movilboxapp.data.utils.EnumValues
import com.brayandev.movilboxapp.data.utils.SortByName
import com.brayandev.movilboxapp.data.utils.applySortItem
import com.brayandev.movilboxapp.data.utils.filterListAndOrder
import com.brayandev.movilboxapp.data.utils.parcelStringToEnum
import com.brayandev.movilboxapp.domain.model.ProductModel
import com.brayandev.movilboxapp.ui.theme.BackgroundItem
import com.brayandev.movilboxapp.ui.theme.TextColor
import com.brayandev.movilboxapp.ui.views.navigation.Routes
import com.brayandev.movilboxapp.ui.views.products.uiState.ProductUiState
import com.brayandev.movilboxapp.ui.views.products.uiState.ProductUiState.Error
import com.brayandev.movilboxapp.ui.views.products.uiState.ProductUiState.Loading
import com.brayandev.movilboxapp.ui.views.products.uiState.ProductUiState.Success

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

    var filterText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    var currentList by rememberSaveable { mutableStateOf(emptyList<ProductModel>()) }
    var sortByType by rememberSaveable { mutableStateOf(SortByName.RATING_HIGH_TO_LOW) }

    var listProducts by rememberSaveable { mutableStateOf(emptyList<ProductModel>()) }

    val categories by productsViewModel.uiStateCategories.collectAsState()

    when (uiState) {
        is Error -> {
            showDialogError()
        }

        Loading -> {
            showLoading()
        }

        is Success -> {
            listProducts = (uiState as Success).list
            currentList = listProducts

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
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround,
                        ) {
                            searchProduct(filterText = filterText, textSelected = {
                                currentList = filterListAndOrder(listProducts, it, sortByType)
                            })

                            filterScreen {
                                sortByType = it
                                currentList = applySortItem(listProducts, it)
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            if (categories.list.isEmpty()) {
                                currentList =
                                    filterListAndOrder(listProducts, filterText.text, sortByType)
                            } else {
                                categorySelected(
                                    categories = categories.list,
                                    categorySelectedFilter = {
                                        currentList = filterListAndOrder(
                                            listProducts,
                                            filterText.text,
                                            sortByType,
                                            it,
                                        )
                                    },
                                )
                            }
                        }

                        if (currentList.isEmpty()) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.ImageNotSupported,
                                    contentDescription = "empty list",
                                )
                                Text(text = stringResource(R.string.text_empty_list))
                            }
                        } else {
                            LazyVerticalGrid(
                                columns = GridCells.Adaptive(180.dp),
                                modifier = Modifier.padding(16.dp),
                                contentPadding = PaddingValues(2.dp),
                            ) {
                                items(currentList) { item ->
                                    ProductItemScreen(item, navController)
                                }
                            }
                        }
                    }
                },
            )
        }
    }
}

@Composable
fun searchProduct(filterText: TextFieldValue, textSelected: (String) -> Unit) {
    var filterText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(filterText)
    }
    OutlinedTextField(
        value = filterText,
        onValueChange = {
            filterText = it
            textSelected(it.text)
        },
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun filterScreen(itemFilterSelected: (SortByName) -> Unit) {
    val sheetStateFilter = rememberModalBottomSheetState()
    var isSheetOpenFilter by rememberSaveable { mutableStateOf(false) }

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
            itemSelected = {
                itemFilterSelected(parcelStringToEnum(it))
            },
            isFilter = true,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun categorySelected(categories: List<String>, categorySelectedFilter: (String) -> Unit) {
    val sheetStateCategory = rememberModalBottomSheetState()
    var isSheetOpenCategory by rememberSaveable { mutableStateOf(false) }

    var selectedCategory by rememberSaveable { mutableStateOf("Categorias") }
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
            text = selectedCategory,
            color = TextColor,
        )
    }
    if (isSheetOpenCategory) {
        MyModalBottomSheet(
            list = categories,
            sheetState = sheetStateCategory,
            isDismissed = { isSheetOpenCategory = it },
            itemSelected = {
                selectedCategory = it
                categorySelectedFilter(it)
            },
            false,
        )
    }
}

@Composable
fun ProductItemScreen(product: ProductModel, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { navController.navigate(Routes.ProductDetail.createRoute(product.id)) },
        colors = CardDefaults.cardColors(containerColor = BackgroundItem),
    ) {
        Column(verticalArrangement = Arrangement.SpaceBetween) {
            Box {
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
    itemSelected: (String) -> Unit,
    isFilter: Boolean,
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            isDismissed(false)
        },
        containerColor = BackgroundItem,
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Icon(
                    imageVector = Icons.Rounded.Category,
                    contentDescription = "icon_filter",
                    tint = TextColor,
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = if (isFilter) {
                        stringResource(R.string.text_filter)
                    } else {
                        stringResource(R.string.text_category)
                    },
                    color = TextColor,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                )
            }
            if (isFilter) {
                EnumValues<SortByName>().forEach { option ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 8.dp)
                            .clickable {
                                itemSelected(option.displayName)
                                isDismissed(false)
                            },
                        elevation = CardDefaults.cardElevation(4.dp),
                        shape = RoundedCornerShape(2.dp),
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = option.displayName,
                            color = TextColor,
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp,
                        )
                    }
                }
            } else {
                LazyColumn(modifier = Modifier.padding(12.dp)) {
                    items(list) { item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 8.dp)
                                .clickable {
                                    itemSelected(item)
                                    isDismissed(false)
                                },
                            elevation = CardDefaults.cardElevation(4.dp),
                            shape = RoundedCornerShape(2.dp),
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = item,
                                color = TextColor,
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun showDialogError() {
    var showDialog by rememberSaveable { mutableStateOf(true) }

    confirmationDialog(show = showDialog, onDismiss = { showDialog = false })

    if (!showDialog) {
        errorScreen()
    }
}

@Composable
fun errorScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.text_message_error),
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Red,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.text_replay),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
fun confirmationDialog(show: Boolean, onDismiss: () -> Unit) {
    if (show) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            confirmButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text(text = stringResource(R.string.text_confir))
                }
            },
            title = { Text(text = stringResource(R.string.text_title_error)) },
            text = { Text(text = stringResource(R.string.text_message_error)) },
        )
    }
}

@Composable
fun showLoading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator()
    }
}
