package com.brayandev.movilboxapp.ui.views.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ProductionQuantityLimits
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.brayandev.movilboxapp.R
import com.brayandev.movilboxapp.ui.theme.BackgroundItem
import com.brayandev.movilboxapp.ui.theme.TextColor
import com.brayandev.movilboxapp.ui.views.navigation.Routes
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    LaunchedEffect(key1 = true) {
        delay(2500)
        navController.popBackStack()
        navController.navigate(Routes.Products.route)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = Icons.Rounded.ProductionQuantityLimits,
            contentDescription = "splash_logo",
            modifier = Modifier.size(150.dp, 150.dp),
            tint = TextColor,
        )
        Text(
            text = stringResource(R.string.text_welcome),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = TextColor,
        )
        CircularProgressIndicator(color = TextColor)
    }
}
