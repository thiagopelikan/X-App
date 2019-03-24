package br.com.pelikan.xapp.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order (

    @PrimaryKey var id: Int,
    var price: Double?,
    var sandwichId: Int?,
    @Ignore var extraIngredientIdList: MutableList<Int>?,
    @Ignore var extraIngredientList: MutableList<Ingredient>?,
    @Ignore var sandwich: Sandwich?,
    @Ignore var promoDiscountList: MutableList<Promotion>?
    ){
    constructor():this(0, null, null,null, null,null,null)

    constructor(id: Int):this(id,null, null,null, null,null,null)

    constructor(id: Int, price: Double, sandwichId: Int, extraIngredientIdList : MutableList<Int>):this(id, price, sandwichId,extraIngredientIdList, null,null,null)

    fun getSandwichRealName() : String?{
        if((extraIngredientList != null) && (extraIngredientList!!.size > 0) && (sandwich != null)){
            return sandwich!!.name + " " +  "- do seu jeito"
        }else if(sandwich != null){
            return sandwich!!.name
        }else{
            return null
        }
    }
}