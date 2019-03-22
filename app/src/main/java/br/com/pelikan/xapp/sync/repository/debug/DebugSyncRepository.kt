package br.com.pelikan.xapp.sync.repository.debug

import br.com.pelikan.xapp.models.DataContext
import br.com.pelikan.xapp.models.Order
import br.com.pelikan.xapp.sync.json.SyncBodyJson
import br.com.pelikan.xapp.sync.repository.SyncRepository
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


class DebugSyncRepository : SyncRepository {

    override fun sync(
        lastUpdate: Long,
        newOrder: Order): Observable<DataContext> {

        val subject = PublishSubject.create<DataContext>()

        var syncBodyJson = SyncBodyJson(lastUpdate, newOrder.id, null, 0.0)

        //TODO SLEEP AND SEND RESPONSE
        //TODO ADD RULE TO DEBUG OR RELEASE TYPES
        //TODO CHANGE NAMING FOR DEBUG AND RELEASE

        return subject
    }
}