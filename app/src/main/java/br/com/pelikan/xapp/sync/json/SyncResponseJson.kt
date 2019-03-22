package br.com.pelikan.xapp.sync.json

import br.com.pelikan.xapp.models.DataContext
import com.google.gson.annotations.SerializedName

class SyncResponseJson(
    @field:SerializedName("last_update") val lastUpdate: Long,
    @field:SerializedName("id_sandwich") val sandwichId: Int,
    @field:SerializedName("extras") val key: List<Int>,
    @field:SerializedName("price") val price: Double
){

    public fun toObject() : DataContext{
        return DataContext();
    }

}