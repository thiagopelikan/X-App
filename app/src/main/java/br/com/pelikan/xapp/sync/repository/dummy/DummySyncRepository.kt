package br.com.pelikan.xapp.sync.repository.dummy

import android.os.Handler
import android.os.Looper
import android.util.Log
import br.com.pelikan.xapp.models.DataContext
import br.com.pelikan.xapp.models.Order
import br.com.pelikan.xapp.sync.repository.SyncRepository
import br.com.pelikan.xapp.utils.FileUtils
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.io.IOException


class DummySyncRepository : SyncRepository {

    override fun sync(
        lastUpdate: Long,
        newOrder: Order?): Observable<DataContext> {

        val subject = PublishSubject.create<DataContext>()

        try {
            val dummyDataContext = Gson().fromJson<DataContext>(FileUtils.readJsonFromAssetsFile("dummy_context.json"), DataContext::class.java)
            Log.d("TAG", "DUMMY DATA CONTEXT " + dummyDataContext.lastUpdate);

            Handler(Looper.getMainLooper()).postDelayed(Runnable { subject.onNext(dummyDataContext) }, 500)

        } catch (e: IOException) {
            e.printStackTrace()
            Handler().postDelayed(Runnable { subject.onError(e) }, 500)
        }

        return subject
    }
}