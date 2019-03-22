package br.com.pelikan.xapp.sync.services

import br.com.pelikan.xapp.sync.json.DataContextBodyJson
import br.com.pelikan.xapp.sync.json.DataContextResponseJson
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface XAppServices {

    @POST("/context")
    fun sync(@Body syncBodyJson: DataContextBodyJson): Call<DataContextResponseJson>
}