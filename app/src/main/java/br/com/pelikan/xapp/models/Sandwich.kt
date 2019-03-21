package br.com.pelikan.xapp.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Sandwich (

    @PrimaryKey var id: Int,
    var name: String?,
    var image: String?,
    @Ignore var ingredientList: List<Ingredient>

){
    constructor():this(0,"","", emptyList())
}