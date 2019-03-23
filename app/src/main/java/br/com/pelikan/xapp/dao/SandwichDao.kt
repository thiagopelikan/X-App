package br.com.pelikan.xapp.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import br.com.pelikan.xapp.models.Sandwich

@Dao
interface SandwichDao {

    @Query("SELECT * from sandwiches")
    fun getAll(): LiveData<List<Sandwich>>

    @Query("SELECT * from sandwiches where id = :sandwichId")
    fun getSandwich(sandwichId : Int): Sandwich?

    @Insert(onConflict = REPLACE)
    fun insert(sandwich: Sandwich)

    @Insert(onConflict = REPLACE)
    fun insertAll(sandwichList: List<Sandwich>)

    @Query("DELETE from sandwiches")
    fun deleteAll()

    @Delete
    fun delete(sandwich: Sandwich)
}