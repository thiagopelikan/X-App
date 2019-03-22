package br.com.pelikan.xapp.models

import com.google.gson.annotations.SerializedName

data class DataContext (

    @SerializedName("last_update") var lastUpdate: Long,
    @SerializedName("sandwiches") var sandwichList: List<Sandwich>?,
    @SerializedName("ingredients") var ingredientList: List<Ingredient>?,
    @SerializedName("promos") var promoList: List<Promotion>?,
    @SerializedName("orders") var orderList: List<Order>?

)