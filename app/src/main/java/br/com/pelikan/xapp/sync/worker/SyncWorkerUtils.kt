package br.com.pelikan.xapp.sync.worker

import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import br.com.pelikan.xapp.models.Order
import br.com.pelikan.xapp.sync.worker.SyncWorker.Companion.ORDER_EXTRA_WORKER
import com.google.gson.Gson
import java.util.*

class SyncWorkerUtils {

    companion object {

        fun callWorker(): UUID {
            val fetchWork = OneTimeWorkRequest.Builder(SyncWorker::class.java)
                .build()
            WorkManager.getInstance().enqueue(fetchWork)
            return fetchWork.id
        }

        fun callWorker(order : Order): UUID {
            val dataBuilder = Data.Builder()
                    dataBuilder.putString(ORDER_EXTRA_WORKER, Gson().toJson(order, Order::class.java))

            val fetchWork = OneTimeWorkRequest.Builder(SyncWorker::class.java)
                .setInputData(dataBuilder.build())
                .build()

            WorkManager.getInstance().enqueue(fetchWork)

            return fetchWork.id
        }
    }
}
