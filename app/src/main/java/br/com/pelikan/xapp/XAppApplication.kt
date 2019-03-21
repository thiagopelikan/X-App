package br.com.pelikan.xapp

import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho

class XAppApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this);
    }
}