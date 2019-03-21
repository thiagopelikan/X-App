package br.com.pelikan.xapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Ingredient (

    @PrimaryKey var id: Int,
    var name: String?,
    var price: Double,
    var image: String?

)