package br.com.pelikan.xapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredients")
data class Ingredient (

    @PrimaryKey var id: Int,
    var name: String?,
    var price: Double?,
    var image: String?

){
    constructor(id: Int):this(id,null,null, null)

    override fun toString(): String {
       return name!!
    }
}