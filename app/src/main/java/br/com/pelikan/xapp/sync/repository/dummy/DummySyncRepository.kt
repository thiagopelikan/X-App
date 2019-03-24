package br.com.pelikan.xapp.sync.repository.dummy

import android.os.Handler
import android.os.Looper
import br.com.pelikan.xapp.models.DataContext
import br.com.pelikan.xapp.sync.payload.DataContextResponseJson
import br.com.pelikan.xapp.sync.repository.SyncRepository
import br.com.pelikan.xapp.utils.FileUtils
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.io.IOException


class DummySyncRepository : SyncRepository {

    override fun sync(
        lastUpdate: Long,
        sandwichId : Int?,
        extraIngredientIdList : List<Int>?,
        price : Double?): Observable<DataContext> {

        val subject = PublishSubject.create<DataContext>()

        try {
            if(sandwichId == null) {
                val dummyDataContext = Gson().fromJson<DataContextResponseJson>(
                    FileUtils.readJsonFromAssetsFile("dummy_context.json"),
                    DataContextResponseJson::class.java
                )
                Handler(Looper.getMainLooper()).postDelayed(
                    Runnable { subject.onNext(dummyDataContext.toObject()) },
                    500
                )
            }else{
                Handler(Looper.getMainLooper()).postDelayed(Runnable { subject.onError(Exception("Adding is not supported on dummy server")) }, 500)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Handler().postDelayed(Runnable { subject.onError(e) }, 500)
        }

        return subject
    }
}