package br.com.pelikan.xapp.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import br.com.pelikan.xapp.models.Ingredient
import br.com.pelikan.xapp.models.Sandwich

@Dao
interface IngredientDao {

    @Query("SELECT * from Ingredient")
    fun getAll(): LiveData<List<Ingredient>>

    @Insert(onConflict = REPLACE)
    fun insert(ingredient: Ingredient)

    @Query("DELETE from Ingredient")
    fun deleteAll()

    @Delete
    fun delete(ingredient: Ingredient)

}