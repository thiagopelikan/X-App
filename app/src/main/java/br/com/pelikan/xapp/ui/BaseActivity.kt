package br.com.pelikan.xapp.ui

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        super.onCreate(savedInstanceState)
        handler = Handler()

    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, 0);
    }
}