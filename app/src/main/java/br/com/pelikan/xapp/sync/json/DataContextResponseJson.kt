package br.com.pelikan.xapp.sync.json

import br.com.pelikan.xapp.models.*
import com.google.gson.annotations.SerializedName

class DataContextResponseJson(
    @field:SerializedName("last_update") val lastUpdate: Long,
    @field:SerializedName("orders") val orderList: List<OrderResponseJson>?,
    @field:SerializedName("promos") val promoList: List<PromosResponseJson>?,
    @field:SerializedName("ingredients") val ingredientList: List<IngredientResponseJson>?,
    @field:SerializedName("sandwiches") val sandwichList: List<SandwichResponseJson>?
){

    fun toObject() : DataContext{
        return DataContext(
            lastUpdate,
            toObjectList(sandwichList),
            toObjectList(ingredientList),
            toObjectList(promoList),
            toObjectList(orderList));
    }

    private fun <T, TT : BaseResponseJson<T>>toObjectList(baseResponseJsonList: List<TT>?) : List<T>?{
        if(baseResponseJsonList == null)
            return null

        val objectList = ArrayList<T>()
        for(item in baseResponseJsonList){
            objectList.add(item.toObject())
        }
        return objectList
    }
}

abstract class BaseResponseJson<T>{
    abstract fun toObject() : T
}

class OrderResponseJson(
        @field:SerializedName("id") val id: Int
    ) : BaseResponseJson<Order>() {

    public override fun toObject() : Order{
        return Order(id);
    }
}

class PromosResponseJson (
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("description") val description: String
) : BaseResponseJson<Promotion>() {

    public override fun toObject() : Promotion{
        return Promotion(id, name, description);
    }
}

class IngredientResponseJson (
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("price") val price: Double,
    @field:SerializedName("image") val image: String
) : BaseResponseJson<Ingredient>() {

    public override fun toObject() : Ingredient{
        return Ingredient(id, name, price, image);
    }
}

class SandwichResponseJson (
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("ingredients") val ingredientList: List<Int>,
    @field:SerializedName("image") val image: String
) : BaseResponseJson<Sandwich>() {

    public override fun toObject() : Sandwich{
        val ingredients = ArrayList<Int>()
        for(ingredientId in ingredientList){
            ingredients.add(ingredientId)
        }
        val sandwich = Sandwich(id, name, image, emptyList())
        sandwich.ingredientIdList = ingredients
        return sandwich;
    }
}