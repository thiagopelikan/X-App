package br.com.pelikan.xapp

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho

class XAppApplication : MultiDexApplication() {

    companion object {
        const val PREFS_LAST_UPDATE : String = "prefs_last_update"

        private lateinit var sharedPreferences: SharedPreferences
        private lateinit var app: Application

        fun getSharedPreferences(): SharedPreferences {
            return sharedPreferences
        }

        fun getApplicationContext(): Context{
            return app.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        app = this
    }

    //TODO IMPLEMENT DETAILS ACTIVITY
    //TODO IMPLEMENT ADD EXTRA INGREDIENTS FEATURE
    //TODO IMPLEMENT ORDERLIST ACTIVITY
    //TODO IMPLEMENT UNIT TEST
    //TODO CHECK/IMPLEMENT ServerSyncRepository
    //TODO TRY TO CONVERT SIMPLESUPPLIER TO KOTLIN
    //TODO RELATÓRIO DE FUNCIONALIDADES A SEREM IMPLEMENTADAS
    //TODO RELATÓRIO DE JUSTIFICATIVAS (ARQUITETURA, LIBS, MELHORIAS SERVIDOR)


}