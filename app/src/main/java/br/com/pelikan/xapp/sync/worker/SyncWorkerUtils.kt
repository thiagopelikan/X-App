package br.com.pelikan.xapp.sync.worker

import androidx.work.*
import java.util.*

class SyncWorkerUtils {

    companion object {

        fun callWorker(): UUID {
            val constraintsBuilder = Constraints.Builder()
            constraintsBuilder.setRequiredNetworkType(NetworkType.CONNECTED)

            val dataBuilder = Data.Builder()
            //        dataBuilder.putLong(DATE_EXTRA_WORKER, localDate.atStartOfDay(zoneBrazilSP).toInstant().toEpochMilli())

            val fetchWork = OneTimeWorkRequest.Builder(SyncWorker::class.java)
                .setInputData(dataBuilder.build())
                .setConstraints(constraintsBuilder.build())
                .build()

            WorkManager.getInstance().enqueue(fetchWork)

            return fetchWork.id
        }
    }
}
