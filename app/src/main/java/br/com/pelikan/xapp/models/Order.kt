package br.com.pelikan.xapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Order (

    @PrimaryKey var id: Int

)