package br.com.pelikan.xapp.sync.repository.release

import br.com.pelikan.xapp.models.DataContext
import br.com.pelikan.xapp.models.Order
import br.com.pelikan.xapp.sync.json.SyncBodyJson
import br.com.pelikan.xapp.sync.json.SyncResponseJson
import br.com.pelikan.xapp.sync.repository.SyncRepository
import br.com.pelikan.xapp.sync.services.XAppApi
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ServiceSyncRepository : SyncRepository {

    override fun sync(
        lastUpdate: Long,
        newOrder: Order): Observable<DataContext> {

        val subject = PublishSubject.create<DataContext>()

        var syncBodyJson = SyncBodyJson(lastUpdate, newOrder.id, null, 0.0)

        val call = XAppApi.services.sync(syncBodyJson)

        call.enqueue(object : Callback<SyncResponseJson> {
            override fun onResponse(call: Call<SyncResponseJson>, response: Response<SyncResponseJson>) {
                try {
                    subject.onNext(response.body()!!.toObject())
                }catch (e : Exception){
                    subject.onError(e)
                }
            }

            override fun onFailure(call: Call<SyncResponseJson>, t: Throwable) {
                subject.onError(t)
            }
        })
        return subject
    }
}