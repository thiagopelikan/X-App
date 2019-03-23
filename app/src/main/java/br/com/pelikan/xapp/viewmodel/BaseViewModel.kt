package br.com.pelikan.xapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import br.com.pelikan.xapp.database.XAppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    val scope = CoroutineScope(coroutineContext)

    var xAppDatabase: XAppDatabase

    init {
        xAppDatabase = XAppDatabase.getDatabase(application, scope)
    }
}