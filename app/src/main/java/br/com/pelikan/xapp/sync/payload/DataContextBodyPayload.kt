package br.com.pelikan.xapp.sync.payload

import com.google.gson.annotations.SerializedName

class DataContextBodyPayload(
    @field:SerializedName("last_update") val lastUpdate: Long,
    @field:SerializedName("id_sandwich") val sandwichId: Int?,
    @field:SerializedName("extras") val key: List<Int>?,
    @field:SerializedName("price") val price: Double?
){
    constructor(lastUpdate: Long) : this(lastUpdate, null, null, null)
}