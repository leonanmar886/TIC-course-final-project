package com.example.restaurantmenu.models

data class Order(
  val dishes: AbstractMutableList<Dish>,
  val totalPrice: Double,
  val orderNumber: Int
) {
}