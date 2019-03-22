package br.com.pelikan.xapp.ui.start

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import br.com.pelikan.xapp.R
import br.com.pelikan.xapp.sync.worker.SyncWorkerUtils
import br.com.pelikan.xapp.ui.BaseActivity
import br.com.pelikan.xapp.ui.main.MainActivity


class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        syncDataContext()
        Handler().postDelayed({goToMainActivity()}, SPLASH_TIME)
    }

    private fun syncDataContext(){
        SyncWorkerUtils.callWorker()
    }

    private fun goToMainActivity(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    companion object {
        const val SPLASH_TIME : Long = 0
    }
}
