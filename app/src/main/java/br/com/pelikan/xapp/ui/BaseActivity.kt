package br.com.pelikan.xapp.ui

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    protected lateinit var mHandler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        super.onCreate(savedInstanceState)
        mHandler = Handler()
//        window.exitTransition = null
//        window.enterTransition = null
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, 0);
    }
}