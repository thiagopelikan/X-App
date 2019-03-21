package br.com.pelikan.xapp.ui.start

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import br.com.pelikan.xapp.ui.BaseActivity
import br.com.pelikan.xapp.ui.main.MainActivity


class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(br.com.pelikan.xapp.R.layout.activity_splash)

        //TODO CALL WORKER

        Handler().postDelayed({goToMainActivity()}, SPLASH_TIME)
    }

    private fun goToMainActivity(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onPause() {
        super.onPause()
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    companion object {
        const val SPLASH_TIME : Long = 0
    }
}
