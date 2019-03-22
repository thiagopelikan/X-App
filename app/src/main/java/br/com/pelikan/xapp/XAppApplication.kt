package br.com.pelikan.xapp

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho

class XAppApplication : MultiDexApplication() {

    companion object {
        const val PREFS_LAST_UPDATE : String = "prefs_last_update"

        private lateinit var sharedPreferences: SharedPreferences
        private lateinit var context: Context

        fun getSharedPreferences(): SharedPreferences {
            return sharedPreferences
        }

        fun getApplicationContext(): Context{
            return context
        }
    }

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        context = applicationContext
    }
}