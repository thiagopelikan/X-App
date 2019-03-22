package br.com.pelikan.xapp.sync.json

import com.google.gson.annotations.SerializedName

class DataContextBodyJson(
    @field:SerializedName("last_update") val lastUpdate: Long,
    @field:SerializedName("id_sandwich") val sandwichId: Int?,
    @field:SerializedName("extras") val key: List<Int>?,
    @field:SerializedName("price") val price: Double?
)