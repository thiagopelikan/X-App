package br.com.pelikan.xapp.sync.services

import br.com.pelikan.xapp.sync.json.SyncBodyJson
import br.com.pelikan.xapp.sync.json.SyncResponseJson
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface XAppServices {

    @POST("/context")
    fun sync(@Body syncBodyJson: SyncBodyJson): Call<SyncResponseJson>
}