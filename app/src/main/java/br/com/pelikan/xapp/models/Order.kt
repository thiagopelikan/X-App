package br.com.pelikan.xapp.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order (

    @PrimaryKey var id: Int,
    var price: Double?,
    var sandwichId: Int,
    @Ignore var extraIngredientList: MutableList<Ingredient>?,
    @Ignore var sandwich: Sandwich?,
    @Ignore var promoDiscountList: MutableList<Promotion>?
    ){
    constructor():this(0, null, 0,null, null,null)

    constructor(id: Int):this(id,null, 0,null, null,null)

    constructor(id: Int, price: Double, sandwichId: Int):this(id, price, sandwichId,null, null,null)

}