package br.com.pelikan.xapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import br.com.pelikan.xapp.models.Order

class OrderViewModel(application: Application) : BaseViewModel(application) {

    private val allOrders: LiveData<List<Order>>

    init {
        val orderDao = xAppDatabase.orderDao()
        allOrders = orderDao.getAll()
    }

    fun getAllOrders(): LiveData<List<Order>> {
        return allOrders
    }
}