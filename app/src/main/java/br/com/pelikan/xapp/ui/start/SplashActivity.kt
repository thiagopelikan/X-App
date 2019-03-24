package br.com.pelikan.xapp.ui.start

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.work.WorkInfo
import androidx.work.WorkManager
import br.com.pelikan.xapp.R
import br.com.pelikan.xapp.XAppApplication
import br.com.pelikan.xapp.sync.worker.SyncWorkerUtils
import br.com.pelikan.xapp.ui.BaseActivity
import br.com.pelikan.xapp.ui.main.MainActivity


class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        syncDataContext()
    }

    private fun syncDataContext(){
        val uuid = SyncWorkerUtils.callWorker()

        val lastUpdate = XAppApplication.getSharedPreferences().getLong(XAppApplication.PREFS_LAST_UPDATE, 0);
        if(lastUpdate > 0){
            Handler().postDelayed({goToMainActivity()}, SPLASH_TIME)
        }else{
            WorkManager.getInstance().getWorkInfoByIdLiveData(uuid).observe(this, Observer { workInfo ->
                if(workInfo.state == WorkInfo.State.SUCCEEDED){
                    goToMainActivity()
                }else if((workInfo.state == WorkInfo.State.FAILED) || (workInfo.state == WorkInfo.State.BLOCKED) || (workInfo.state == WorkInfo.State.CANCELLED)){
                    AlertDialog.Builder(this)
                        .setTitle(R.string.error_sync_title)
                        .setMessage(R.string.error_default)
                        .setNeutralButton(R.string.ok) { _, _ -> syncDataContext() }.show()
                }
            })
        }
    }

    private fun goToMainActivity(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    companion object {
        const val SPLASH_TIME : Long = 2000
    }
}
