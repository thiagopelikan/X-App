package br.com.pelikan.xapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Promotion (

    @PrimaryKey var id: Int,
    var name: String?,
    var description: String?

)