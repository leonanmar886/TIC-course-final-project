package com.example.restaurantmenu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.restaurantmenu.theme.RestaurantMenuTheme
import com.example.restaurantmenu.theme.md_theme_light_onPrimary
import com.example.restaurantmenu.theme.md_theme_light_primary
import com.example.restaurantmenu.theme.md_theme_light_primaryContainer

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      RestaurantMenuTheme {
        Surface(
          modifier = Modifier.fillMaxSize()
            .background(md_theme_light_primaryContainer)
        ) {
          RestaurantMenuApp()
        }
      }
    }
  }
}

@Composable
fun RestaurantMenuApp() {
  Scaffold (
    topBar = {
      Box(modifier = Modifier
        .fillMaxWidth()
        .background(md_theme_light_primary)
      )
      {
        Text(
          text = stringResource(id = R.string.restaurant_name),
          modifier = Modifier
            .align(Alignment.Center)
            .padding(10.dp),
          color = md_theme_light_onPrimary
          )

      }
    }
  ){
    LazyColumn(contentPadding = it){
      items(dishes){

      }
    }
  }
}

@Preview
@Composable
fun RestaurantMenuAppPreview() {
  RestaurantMenuTheme {
    RestaurantMenuApp()
  }
}