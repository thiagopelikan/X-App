package br.com.pelikan.xapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import br.com.pelikan.xapp.models.Promotion

class PromoViewModel(application: Application) : BaseViewModel(application) {

    private val allPromos: LiveData<List<Promotion>>

    init {
        val promotionDao = xAppDatabase.promotionDao()
        allPromos = promotionDao.getAll()
    }

    fun getAllPromos(): LiveData<List<Promotion>> {
        return allPromos
    }
}