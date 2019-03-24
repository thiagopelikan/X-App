package br.com.pelikan.xapp.sync.repository.server

import br.com.pelikan.xapp.models.DataContext
import br.com.pelikan.xapp.models.Order
import br.com.pelikan.xapp.sync.payload.DataContextBodyPayload
import br.com.pelikan.xapp.sync.payload.DataContextResponseJson
import br.com.pelikan.xapp.sync.repository.SyncRepository
import br.com.pelikan.xapp.sync.services.XAppApi
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ServerSyncRepository : SyncRepository {

    override fun sync(
        lastUpdate: Long,
        sandwichId : Int?,
        extraIngredientIdList : List<Int>?,
        price : Double?): Observable<DataContext> {

        val subject = PublishSubject.create<DataContext>()

        val call = XAppApi.services.sync(DataContextBodyPayload(lastUpdate, sandwichId, extraIngredientIdList, price))

        call.enqueue(object : Callback<DataContextResponseJson> {
            override fun onResponse(call: Call<DataContextResponseJson>, response: Response<DataContextResponseJson>) {
                try {
                    subject.onNext(response.body()!!.toObject())
                }catch (e : Exception){
                    subject.onError(e)
                }
            }

            override fun onFailure(call: Call<DataContextResponseJson>, t: Throwable) {
                subject.onError(t)
            }
        })
        return subject
    }
}