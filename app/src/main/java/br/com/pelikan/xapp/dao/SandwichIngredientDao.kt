package br.com.pelikan.xapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.RoomWarnings
import br.com.pelikan.xapp.models.Ingredient
import br.com.pelikan.xapp.models.SandwichIngredient

@Dao
interface SandwichIngredientDao {

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("""
        SELECT * FROM Ingredient INNER JOIN SandwichIngredient ON
        Ingredient.id = SandwichIngredient.ingredientId WHERE
        SandwichIngredient.sandwichId = :sandwichId
        """)
    fun getIngredientsFromSandwich(sandwichId: Int): List<Ingredient>

    @Query("DELETE from SandwichIngredient")
    fun deleteAll()

    @Insert(onConflict = REPLACE)
    fun insert(sandwichIngredient: SandwichIngredient);
}