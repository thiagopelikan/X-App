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

    @Query("SELECT * from ingredients")
    fun getAll(): LiveData<List<Ingredient>>

    @Query("SELECT * from ingredients where id = :ingredientId")
    fun getIngredient(ingredientId : Int): Ingredient?

    @Insert(onConflict = REPLACE)
    fun insert(ingredient: Ingredient)

    @Insert(onConflict = REPLACE)
    fun insertAll(ingredientList: List<Ingredient>)

    @Query("DELETE from ingredients")
    fun deleteAll()

    @Delete
    fun delete(ingredient: Ingredient)

}