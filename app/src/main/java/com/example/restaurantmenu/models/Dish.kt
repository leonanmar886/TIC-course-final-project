package com.example.restaurantmenu.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.restaurantmenu.enums.Category

data class Dish(
  @StringRes val name: Int,
  @StringRes val description: Int,
  val price: Double,
  @DrawableRes val image: Int,
  val category: Category,
  val preparationTime: Double
) {
}