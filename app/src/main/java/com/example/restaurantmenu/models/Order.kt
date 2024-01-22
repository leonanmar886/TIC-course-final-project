package com.example.restaurantmenu.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf

data class Order(
  var dishes: MutableList<Dish>,
  var totalPrice: MutableState<Double> = mutableDoubleStateOf(0.0),
  var orderNumber: Int
) {
  constructor() : this(mutableListOf(), mutableDoubleStateOf(0.0), 0)
  fun addDish(dish: Dish) {
    if (dishes.contains(dish)) {
      return
    }
    dishes.add(dish)
  }

  fun removeDish(dish: Dish) {
    if (!dishes.contains(dish)) {
      return
    }
    dishes.remove(dish)
  }

  fun calculateTotalPrice(): Double {
    var total = 0.0
    for (dish in dishes) {
      total += dish.price
    }
    totalPrice.value = total
    return total
  }
}