package br.com.pelikan.xapp.sync.worker

import android.content.Context
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import androidx.work.impl.utils.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import br.com.pelikan.xapp.XAppApplication
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.work.*
import br.com.pelikan.xapp.models.Order
import com.google.gson.Gson


@RunWith(JUnit4::class)
class SyncWorkerTest {

    private var sharedPreferences: SharedPreferences? = null

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val config = Configuration.Builder()
            // Set log level to Log.DEBUG to make it easier to debug
            .setMinimumLoggingLevel(Log.DEBUG)
            // Use a SynchronousExecutor here to make it easier to write tests
            .setExecutor(SynchronousExecutor())
            .build()

        // Initialize WorkManager for instrumentation tests.
        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)
    }

    @Test
    @Throws(Exception::class)
    fun testSync() {
        sharedPreferences!!.edit().putLong(XAppApplication.PREFS_LAST_UPDATE, 0).apply();

        // Create request
        val request = OneTimeWorkRequestBuilder<SyncWorker>()

            .build()

        val workManager = WorkManager.getInstance()
        workManager.enqueue(request).result.get()

        Thread.sleep(2000);

        val workInfo = workManager.getWorkInfoById(request.id).get()

        // Assert
        assertThat(workInfo.state, `is`(WorkInfo.State.SUCCEEDED) )
        assertThat(sharedPreferences!!.getLong(XAppApplication.PREFS_LAST_UPDATE, 0), `is`(1553254921954) )
    }

    @Test
    @Throws(Exception::class)
    fun testSyncWithNewOrderFails() {

        val order = Order(1, 10.0, 1)
        val dataBuilder = Data.Builder()
        dataBuilder.putString(SyncWorker.ORDER_EXTRA_WORKER, Gson().toJson(order, Order::class.java))

        sharedPreferences!!.edit().putLong(XAppApplication.PREFS_LAST_UPDATE, 0).apply();

        // Create request
        val request = OneTimeWorkRequestBuilder<SyncWorker>()
            .setInputData(dataBuilder.build())
            .build()

        val workManager = WorkManager.getInstance()
        workManager.enqueue(request).result.get()

        Thread.sleep(2000);

        val workInfo = workManager.getWorkInfoById(request.id).get()

        // Assert
        assertThat(workInfo.state, `is`(WorkInfo.State.FAILED) )
        assertThat(sharedPreferences!!.getLong(XAppApplication.PREFS_LAST_UPDATE, 0), `is`(0L) )
    }

}