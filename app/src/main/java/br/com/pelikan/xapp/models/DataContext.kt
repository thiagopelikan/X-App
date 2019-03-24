package br.com.pelikan.xapp.models

data class DataContext (

    var lastUpdate: Long,
    var sandwichList: List<Sandwich>?,
    var ingredientList: List<Ingredient>?,
    var promoList: List<Promotion>?,
    var orderList: List<Order>?

)