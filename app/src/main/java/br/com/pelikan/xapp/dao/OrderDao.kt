package br.com.pelikan.xapp.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import br.com.pelikan.xapp.models.Order

@Dao
interface OrderDao {

    @Query("SELECT * from orders")
    fun getAll(): LiveData<List<Order>>

    @Insert(onConflict = REPLACE)
    fun insert(order: Order)

    @Insert(onConflict = REPLACE)
    fun insertAll(orderList: List<Order>)

    @Query("DELETE from orders")
    fun deleteAll()

    @Delete
    fun delete(order: Order)

}