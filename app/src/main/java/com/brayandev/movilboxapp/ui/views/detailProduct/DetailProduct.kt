package com.brayandev.movilboxapp.ui.views.detailProduct

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Upgrade
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.brayandev.movilboxapp.R
import com.brayandev.movilboxapp.domain.model.ProductModel
import com.brayandev.movilboxapp.ui.theme.BackgroundItem
import com.brayandev.movilboxapp.ui.theme.TextColor
import com.brayandev.movilboxapp.ui.views.detailProduct.uiState.DetailUiState
import com.brayandev.movilboxapp.ui.views.detailProduct.uiState.DetailUiState.Error
import com.brayandev.movilboxapp.ui.views.detailProduct.uiState.DetailUiState.Loading
import com.brayandev.movilboxapp.ui.views.detailProduct.uiState.DetailUiState.Success
import com.brayandev.movilboxapp.ui.views.products.showDialogError
import com.brayandev.movilboxapp.ui.views.products.showLoading
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import java.lang.Thread.yield
import kotlin.math.roundToInt

@Composable
fun DetailProductScreen(viewModel: DetailProductViewModel, navController: NavHostController) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val uiState by produceState<DetailUiState>(
        initialValue = Loading,
        key1 = lifecycle,
        key2 = viewModel,
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.uiState.collect { value = it }
        }
    }

    val isProductDeleted by viewModel.isDeleted.observeAsState(initial = false)

    when (uiState) {
        is Error -> {
            showDialogError()
        }

        Loading -> {
            showLoading()
        }

        is Success -> {
            detailScreen((uiState as Success).product, navController, viewModel, isProductDeleted)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun detailScreen(
    product: ProductModel,
    navController: NavHostController,
    viewModel: DetailProductViewModel,
    isProductDeleted: Boolean,
) {
    var isDisableText by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = stringResource(R.string.text_title_detail),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            color = TextColor,
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Rounded.ArrowBack,
                            contentDescription = "back",
                            tint = TextColor,
                        )
                    }
                },
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(
                        rememberScrollState(),
                    ),
            ) {
                if (isProductDeleted) {
                    ShowGeneralDialog(
                        navController,
                        stringResource(R.string.text_warning),
                        stringResource(R.string.text_product_delete_success),
                    )
                } else {
                    ImageSlider(product.images)
                    DetailScreen(product, isDisableText)
                    ItemsDetail(product, viewModel) {
                        isDisableText = it
                    }
                }
            }
        },
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageSlider(images: List<String>) {
    val imagePagerState = rememberPagerState(initialPage = images.size)

    LaunchedEffect(Unit) {
        while (true) {
            yield()
            delay(2000)
            imagePagerState.animateScrollToPage(
                page = (imagePagerState.currentPage + 1) % (imagePagerState.pageCount),
            )
        }
    }

    Card(
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = BackgroundItem),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
        ) {
            HorizontalPager(
                count = images.size,
                state = imagePagerState,
            ) { page ->
                AsyncImage(
                    model = images[page],
                    contentDescription = "carousel",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(2 / 2f),
                    alignment = Alignment.Center,
                )
            }
        }
    }
}

@Composable
fun DetailScreen(product: ProductModel, isDisableText: Boolean) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = BackgroundItem),
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = product.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
            )
            Text(
                text = product.description,
                color = TextColor,
            )

            itemsDetailProduct(product = product, isDisableText)
            RatingBarProduct(rating = product.rating)
        }
    }
}

@Composable
fun RatingBarProduct(rating: Double) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = stringResource(R.string.text_rating), color = TextColor)
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp),
            progress = rating.toFloat() / 5,
            color = TextColor,
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Gray)
                .height(16.dp)
                .background(BackgroundItem)
                .widthIn(
                    min = 0.dp,
                    max = (rating.toInt() * 20).dp,
                ),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun itemsDetailProduct(product: ProductModel, isDisableText: Boolean) {
    val discount = product.price * (1 - product.discountPercentage / 100)
    var textDiscount by rememberSaveable { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            .border(1.dp, BackgroundItem, RoundedCornerShape(4.dp)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp),
        ) {
            Text(
                text = "Precio: ",
                color = TextColor,
            )
            Text(
                text = "Stock: ",
                color = TextColor,
            )
            Text(
                text = "Descuento: ",
                color = TextColor,
            )
            Text(
                text = "Precio Final: ",
                color = TextColor,
            )
        }
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .padding(8.dp),
        ) {
            Text(
                text = "$${product.price}",
                color = TextColor,
                textAlign = TextAlign.End,
            )
            Text(
                text = "$${product.stock}",
                color = TextColor,
                textAlign = TextAlign.End,
            )
            Text(
                text = "-${product.discountPercentage}%",
                color = TextColor,
                textAlign = TextAlign.End,
            )
            Text(
                text = "$${discount.roundToInt()}",
                color = TextColor,
                textAlign = TextAlign.End,
            )
            /*OutlinedTextField(
                value = textDiscount,
                onValueChange = { textDiscount },
                modifier = Modifier.padding(20.dp),
                label = { Text(text = "$ ${discount.roundToInt()}") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BackgroundItem,
                    unfocusedBorderColor = TextColor,
                ),
            )*/
        }
    }
}

@Composable
fun ItemsDetail(
    product: ProductModel,
    viewModel: DetailProductViewModel,
    onUpdate: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Button(
            onClick = { onUpdate(true) },
            colors = ButtonDefaults.buttonColors(containerColor = BackgroundItem),
            shape = RoundedCornerShape(4.dp),
        ) {
            Icon(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                imageVector = Icons.Rounded.Upgrade,
                contentDescription = "Icon_upgrade",
                tint = TextColor,
            )
            Text(text = "Actualizar", color = TextColor)
        }
        Button(
            onClick = { viewModel.deleteProduct(product) },
            colors = ButtonDefaults.buttonColors(containerColor = BackgroundItem),
            shape = RoundedCornerShape(4.dp),
        ) {
            Icon(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                imageVector = Icons.Rounded.Delete,
                contentDescription = "Icon_delete",
                tint = Color.Red,
            )
            Text(text = "Eliminar", color = TextColor)
        }
    }
}

@Composable
fun ShowGeneralDialog(navController: NavHostController, title: String, message: String) {
    AlertDialog(
        onDismissRequest = { navController.popBackStack() },
        confirmButton = {
            TextButton(onClick = { navController.popBackStack() }) {
                Text(text = "Ok")
            }
        },
        title = { Text(text = title) },
        text = { Text(text = message) },
    )
}
