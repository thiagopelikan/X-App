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

    @Query("SELECT * from Sandwich")
    fun getAll(): LiveData<List<Sandwich>>

    @Insert(onConflict = REPLACE)
    fun insert(sandwich: Sandwich)

    @Query("DELETE from Sandwich")
    fun deleteAll()

    @Delete
    fun delete(sandwich: Sandwich)
}