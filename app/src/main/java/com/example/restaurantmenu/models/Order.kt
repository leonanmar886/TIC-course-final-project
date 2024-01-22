package com.example.restaurantmenu.models

data class Order(
  var dishes: MutableList<Dish>,
  var totalPrice: Double,
  var orderNumber: Int
) {
  constructor() : this(mutableListOf(), 0.0, 0)
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
    return total
  }
}