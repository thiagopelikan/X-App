package br.com.pelikan.xapp.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "sandwiches")
data class Sandwich (

    @PrimaryKey var id: Int,
    var name: String,
    var image: String?,
    @Ignore var ingredientList: List<Ingredient>,
    @Ignore var price: Double?

){
    constructor():this(0,"","", emptyList(), null)

    constructor(id: Int):this(id,"","", emptyList(), null)

    constructor(id: Int, name: String, image: String?):this(id, name, image, emptyList(), null)

}