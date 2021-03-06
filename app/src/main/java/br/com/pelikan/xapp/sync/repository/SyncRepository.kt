package br.com.pelikan.xapp.sync.repository

import br.com.pelikan.xapp.models.DataContext
import br.com.pelikan.xapp.models.Order
import io.reactivex.Observable

interface SyncRepository {
    fun sync(
         lastUpdate: Long,
         sandwichId : Int?,
         extraIngredientIdList : List<Int>?,
         price : Double?
    ): Observable<DataContext>
}