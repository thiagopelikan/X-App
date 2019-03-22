package br.com.pelikan.xapp.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "sandwiches")
data class Sandwich (

    @PrimaryKey var id: Int,
    var name: String?,
    var image: String?,
    @Ignore var ingredientList: List<Ingredient>,
    @SerializedName("ingredients") @Ignore var ingredientIdList: List<Int>,
    @Ignore var price: Double?

){
    constructor():this(0,"","", emptyList(), emptyList(), null)

    constructor(id: Int, name: String?, image: String?, ingredientList: List<Ingredient>):this(id,name,image, ingredientList, emptyList(), null)
}