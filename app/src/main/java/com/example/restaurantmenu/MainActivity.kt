package com.example.restaurantmenu

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.restaurantmenu.enums.Category
import com.example.restaurantmenu.models.Dish
import com.example.restaurantmenu.models.Order
import com.example.restaurantmenu.theme.RestaurantMenuTheme
import com.example.restaurantmenu.theme.md_theme_light_onPrimary
import com.example.restaurantmenu.theme.md_theme_light_onPrimaryContainer
import com.example.restaurantmenu.theme.md_theme_light_primary
import com.example.restaurantmenu.theme.md_theme_light_primaryContainer

val grouped = dishes.groupBy { it.category }

val order = Order()

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
    },
    bottomBar = {
        BottomAppBar()
    }
  ){
    DishesList(
      grouped = grouped,
      contentPadding = it
    )
  }
}

@Composable
fun BottomAppBar(){
  val context = LocalContext.current
  val finishOrderToastMessage = if(order.dishes.isEmpty()) stringResource(R.string.dish_list_empty) else stringResource(R.string.order_finished)

  fun handleFinishOrder(){
    Toast.makeText(context, finishOrderToastMessage, Toast.LENGTH_LONG).show()
  }

  Row(
    modifier = Modifier
      .fillMaxWidth()
      .height(90.dp)
      .background(md_theme_light_primary),
    verticalAlignment = Alignment.CenterVertically
  )
  {
    Column(
      modifier = Modifier.weight(1f),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        text = stringResource(R.string.total,
          order.totalPrice.value),
        color = md_theme_light_onPrimary,
        style = MaterialTheme.typography.bodyLarge
      )
    }

    Column(
      modifier = Modifier.weight(1f),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Button(onClick = { handleFinishOrder() }, colors = ButtonDefaults.buttonColors(containerColor = md_theme_light_primaryContainer)) {
        Text(
          text = stringResource(R.string.finish_order),
          color = md_theme_light_onPrimaryContainer,
          style = MaterialTheme.typography.bodyLarge
        )
      }
    }
  }
}

@Composable
fun DishItem(dish: Dish){
  var dishState by remember { mutableStateOf(false) }
  fun updateOrder(newValue: Boolean){
    if(newValue){
      order.addDish(dish)
    } else {
      order.removeDish(dish)
    }
    order.calculateTotalPrice()
    dishState = newValue
  }

  Row(
    modifier = Modifier
      .fillMaxWidth()
      .height(60.dp)
      .background(md_theme_light_primaryContainer),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ){
    Box(
      modifier = Modifier
        .size(35.dp),
      contentAlignment = Alignment.Center,
    ){
      Checkbox(
        checked = dishState,
        onCheckedChange = { updateOrder(it) },
        modifier = Modifier.scale(scale = 0.6f)
      )
    }
    DishImage(imageRes = dish.image)
    DishInfo(
      dishName = dish.name,
      dishDescription = dish.description,
      dishPrepareTime = dish.preparationTime,
      modifier = Modifier,
    )
  }
}

@Composable
fun DishInfo(
  @StringRes dishName: Int,
  @StringRes dishDescription: Int,
  dishPrepareTime: Double,
  modifier: Modifier,
){
  val prepareTimeString = if(dishPrepareTime > 1.0){
    stringResource(R.string.prepare_time, dishPrepareTime) + " minutos"
  } else if (dishPrepareTime == 1.0){
    stringResource(R.string.prepare_time, dishPrepareTime) + " minuto"
  } else {
    stringResource(R.string.prepare_time_null)
  }

  var observations by remember { mutableStateOf("") }

  Column(
    modifier = modifier
      .padding(horizontal = 8.dp, vertical = 8.dp)
  ){
    Text(
      text = stringResource(dishName),
      color = md_theme_light_primary,
      style = MaterialTheme.typography.bodyLarge
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      text = stringResource(dishDescription),
      color = md_theme_light_primary,
      style = MaterialTheme.typography.labelSmall,
      modifier = modifier.fillMaxWidth(0.5f)
    )
  }
  Column (
    modifier = modifier
      .padding(horizontal = 7.dp, vertical = 8.dp)
      .fillMaxSize(),
    horizontalAlignment = Alignment.Start,
    verticalArrangement = Arrangement.Top
  ) {
    Text(
      text = prepareTimeString,
      color = md_theme_light_primary,
      style = MaterialTheme.typography.labelSmall
    )
    Spacer(modifier = Modifier.height(8.dp))
    TextField(
      label = {
        Text("Enter text", modifier = Modifier.align(Alignment.Start))
      },
      maxLines = 2,
      textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
      modifier = Modifier.height(8.dp),
      colors = TextFieldDefaults.colors(
        unfocusedContainerColor = md_theme_light_primaryContainer,
        focusedContainerColor = md_theme_light_primaryContainer,
        focusedTextColor = md_theme_light_primary,
        focusedIndicatorColor = md_theme_light_primary,
        unfocusedIndicatorColor = md_theme_light_primary
      ),
      value = observations,
      onValueChange = {observations = it}
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
      text = stringResource(R.string.price, dishPrepareTime),
      color = md_theme_light_primary,
      style = MaterialTheme.typography.labelSmall
    )
  }
}

@Composable
fun DishImage(
  @DrawableRes imageRes: Int
){
  Box{
    Image(
      modifier = Modifier
        .size(dimensionResource(R.dimen.image_size))
        .padding(dimensionResource(R.dimen.padding_small))
        .clip(MaterialTheme.shapes.small),
      painter = painterResource(imageRes),
      contentDescription = null
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
          Column(modifier = Modifier
            .weight(1f)
            .background(md_theme_light_primary)) {
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

          Column(modifier = Modifier
            .weight(1f)
            .background(md_theme_light_primary)) {
            Box(
              modifier = Modifier
                .height(height = 1.dp)
            )
          }
        }
      }

      items(dishes) { dish ->
        DishItem(dish)
        Spacer(modifier = Modifier.height(8.dp))
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