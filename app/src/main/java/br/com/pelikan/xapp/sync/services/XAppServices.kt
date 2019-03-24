package br.com.pelikan.xapp.sync.services

import br.com.pelikan.xapp.sync.payload.DataContextBodyPayload
import br.com.pelikan.xapp.sync.payload.DataContextResponseJson
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface XAppServices {

    @POST("/api/context")
    fun sync(@Body syncBodyPayload: DataContextBodyPayload): Call<DataContextResponseJson>
}