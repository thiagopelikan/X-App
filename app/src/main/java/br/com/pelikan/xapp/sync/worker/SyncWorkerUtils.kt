package br.com.pelikan.xapp.sync.worker

import androidx.work.*
import java.util.*

class SyncWorkerUtils {

    companion object {

        fun callWorker(): UUID {
            val dataBuilder = Data.Builder()
            //        dataBuilder.putLong(DATE_EXTRA_WORKER, localDate.atStartOfDay(zoneBrazilSP).toInstant().toEpochMilli())

            val fetchWork = OneTimeWorkRequest.Builder(SyncWorker::class.java)
                .setInputData(dataBuilder.build())
                .build()

            WorkManager.getInstance().enqueue(fetchWork)

            return fetchWork.id
        }
    }
}
