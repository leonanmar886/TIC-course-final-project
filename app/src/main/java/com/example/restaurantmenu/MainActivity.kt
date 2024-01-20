package com.example.restaurantmenu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
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
import com.example.restaurantmenu.enums.Category
import com.example.restaurantmenu.models.Dish
import com.example.restaurantmenu.theme.RestaurantMenuTheme
import com.example.restaurantmenu.theme.md_theme_light_onPrimary
import com.example.restaurantmenu.theme.md_theme_light_primary
import com.example.restaurantmenu.theme.md_theme_light_primaryContainer

val grouped = dishes.groupBy { it.category }

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      RestaurantMenuTheme {
        Surface(
          modifier = Modifier
            .fillMaxSize()
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
        .height(60.dp)
        .background(md_theme_light_primary)
      )
      {
        Text(
          text = stringResource(id = R.string.restaurant_name),
          modifier = Modifier
            .align(Alignment.Center),
          color = md_theme_light_onPrimary,
          style = MaterialTheme.typography.displayLarge
          )

      }
    }
  ){
    DishesList(
      grouped = grouped,
      contentPadding = it
    )
  }
}

@Composable
fun DishItem(dish: Dish){
  Box(modifier = Modifier
    .fillMaxWidth()
    .height(64.dp)
    .background(Color.White)
    .padding(horizontal = 16.dp)
    .paddingFromBaseline(top = 24.dp)
    .background(md_theme_light_primaryContainer)
  ){
    Text(
      text = stringResource(dish.name),
      modifier = Modifier
        .align(Alignment.CenterStart),
      color = md_theme_light_onPrimary,
      style = MaterialTheme.typography.bodyLarge
    )
    Text(
      text = dish.price.toString(),
      modifier = Modifier
        .align(Alignment.CenterEnd),
      color = md_theme_light_onPrimary,
      style = MaterialTheme.typography.bodyLarge
    )
  }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DishesList(
  grouped: Map<Category, List<Dish>>,
  contentPadding: PaddingValues
){
  LazyColumn(contentPadding = contentPadding){
    grouped.forEach{ (category, dishes) ->
      stickyHeader {
        Row(
          modifier = Modifier
            .fillMaxWidth(),
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically
        ){
          Column(modifier = Modifier.weight(1f).background(md_theme_light_primary)) {
            Box(
              modifier = Modifier
                .height(height = 1.dp)
                .background(color = Color.Blue)
            )
          }

          Spacer(modifier = Modifier.width(3.dp))

          Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally

          ) {
            Text(
              text = category.value,
              modifier = Modifier
                .paddingFromBaseline(top = 24.dp, bottom = 8.dp),
              color = md_theme_light_primary,
              style = MaterialTheme.typography.displayMedium
            )
          }

          Spacer(modifier = Modifier.width(3.dp))

          Column(modifier = Modifier.weight(1f).background(md_theme_light_primary)) {
            Box(
              modifier = Modifier
                .height(height = 1.dp)
            )
          }
        }
      }

      items(dishes) { dish ->
        DishItem(dish)
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